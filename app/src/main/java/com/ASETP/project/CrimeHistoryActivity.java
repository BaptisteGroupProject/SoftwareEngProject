package com.ASETP.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ASETP.project.adapter.CrimeAdapter;
import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityCrimeHistoryBinding;
import com.ASETP.project.location.AndroidScheduler;
import com.ASETP.project.location.GoogleMapLocation;
import com.ASETP.project.utils.QueryFactory;
import com.amazonaws.amplify.generated.graphql.ListCrimeDatasQuery;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.Nonnull;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import type.ModelCrimeDataFilterInput;
import type.ModelFloatInput;

import static com.ASETP.project.MainActivity.EARTH_RADIUS;

public class CrimeHistoryActivity extends BaseActivity<ActivityCrimeHistoryBinding> implements OnMapReadyCallback {

    private LatLng latLng;

    private double minLat, maxLat, minLon, maxLon;

    private CrimeAdapter adapter;

    private List<ListCrimeDatasQuery.Item> data = new ArrayList<>();

    private RecyclerView crimeList;

    private TextView noData;

    private GoogleMapLocation mapLocation;

    private boolean mapFinish = false, fetchFinish = false;


    public static void startCrimeHistoryActivity(Context context, LatLng lat) {
        Intent intent = new Intent(context, CrimeHistoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DashboardActivity.POSITION, lat);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void init(Bundle bundle) {
        latLng = getIntent().getParcelableExtra(DashboardActivity.POSITION);
        initToolBar(binding.appbar.toolbar, true, "Crime History Around 5 KM");
        QueryFactory.init(this);
        setNav();
        around5Km();

        mapLocation = new GoogleMapLocation(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crime_history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.crimeHistory) {
            binding.drawerLayout.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNav() {
        View view = binding.nav.getHeaderView(0);
        crimeList = view.findViewById(R.id.list);
        noData = view.findViewById(R.id.noData);
    }

    private void around5Km() {
        //5KM
        double dis = 5;
        double dLng = 2 * Math.asin(Math.sin(dis / (2 * EARTH_RADIUS)) / Math.cos(latLng.latitude * Math.PI / 180));
        dLng = dLng * 180 / Math.PI;
        double dLat = dis / EARTH_RADIUS;
        dLat = dLat * 180 / Math.PI;
        minLat = latLng.latitude - dLat;
        maxLat = latLng.latitude + dLat;
        minLon = latLng.longitude - dLng;
        maxLon = latLng.longitude + dLng;
        getCrimeData();
    }

    private void getCrimeData() {
        List<Double> latBetween = new ArrayList<>();
        List<Double> lonBetween = new ArrayList<>();
        latBetween.add(minLat);
        latBetween.add(maxLat);
        lonBetween.add(minLon);
        lonBetween.add(maxLon);
        ModelCrimeDataFilterInput filterInput = ModelCrimeDataFilterInput.builder()
                .latitude(ModelFloatInput.builder().between(latBetween).build()).longitude(ModelFloatInput.builder().between(lonBetween).build()).build();
        Completable.create(emitter -> QueryFactory.awsAppSyncClient().query(ListCrimeDatasQuery.builder().filter(filterInput).build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(callback(emitter))).subscribeOn(Schedulers.io()).observeOn(AndroidScheduler.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(tag, "start read");
                        showWaitDialog("loading...");
                    }

                    @Override
                    public void onComplete() {
                        hideWaitDialog();
                        adapter = new CrimeAdapter(CrimeHistoryActivity.this, data);
                        crimeList.setLayoutManager(new LinearLayoutManager(CrimeHistoryActivity.this));
                        crimeList.setAdapter(adapter);
                        fetchFinish = true;
                        setMarker();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        Log.e(tag, "search error", e);
                        noData.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void setMarker() {
        if (!fetchFinish || !mapFinish) {
            return;
        }
        for (ListCrimeDatasQuery.Item item : data) {
            LatLng latLng = new LatLng(item.latitude(), item.longitude());
            mapLocation.addLocationPlaceMarker(latLng, item.CrimeType());
        }
    }

    private GraphQLCall.Callback<ListCrimeDatasQuery.Data> callback(CompletableEmitter emitter) {
        return new GraphQLCall.Callback<ListCrimeDatasQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<ListCrimeDatasQuery.Data> response) {
                if (response.data() == null) {
                    Log.e(tag, "data null");
                    return;
                }
                Log.e(tag, response.data().toString());
                sortData(response.data().listCrimeDatas().items());
                emitter.onComplete();
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(tag, "fetch error", e);
            }
        };
    }

    private void sortData(List<ListCrimeDatasQuery.Item> unSort) {
        if (unSort.size() == 0) {
            noData.setVisibility(View.VISIBLE);
        }
        for (ListCrimeDatasQuery.Item item : unSort) {
            boolean isRepeat = false;
            if (item.longitude() > minLon && item.longitude() < maxLon) {
                for (ListCrimeDatasQuery.Item forRepeat : data) {
                    if (forRepeat.id().equals(item.id())) {
                        isRepeat = true;
                        break;
                    }
                }
                if (!isRepeat) {
                    data.add(item);
                }
            }
        }
        sortByDate();
    }

    private void sortByDate() {
        Collections.sort(data, (o1, o2) -> {
            try {
                return Objects.requireNonNull(new SimpleDateFormat("yyyy-MM", Locale.ENGLISH)
                        .parse(o2.month()))
                        .compareTo(new SimpleDateFormat("yyyy-MM", Locale.ENGLISH).parse(o1.month()));
            } catch (ParseException e) {
                return 0;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapLocation.setGoogleMap(googleMap);
        mapLocation.updateLocationUi();
        mapLocation.addHouseLocation(latLng);
        mapLocation.getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        mapFinish = true;
        setMarker();
        mapLocation.getGoogleMap().setOnMyLocationButtonClickListener(() -> {
            mapLocation.getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            return true;
        });
    }


}
