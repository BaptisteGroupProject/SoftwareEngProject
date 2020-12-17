package com.ASETP.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ASETP.project.adapter.HistoryAdapter;
import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityDashboardBinding;
import com.ASETP.project.location.AndroidScheduler;
import com.ASETP.project.model.PlacePaidData;
import com.ASETP.project.utils.QueryFactory;
import com.amazonaws.amplify.generated.graphql.QueryByPostcodeQuery;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author Owner
 */
public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> implements View.OnClickListener {

    private String postcode;

    public static String POSTCODE = "postcode";

    public static String POSITION = "lat";

    private LatLng lat;

    private List<PlacePaidData> data = new ArrayList<>();

    List<Entry> values = new ArrayList<>();

    List<String> xLabel = new ArrayList<>();

    public static void startDashboardActivity(Context context, String postcode, LatLng latLng) {
        Intent intent = new Intent(context, DashboardActivity.class);
        intent.putExtra(POSTCODE, postcode);
        Bundle bundle = new Bundle();
        bundle.putParcelable(POSITION, latLng);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle bundle) {
        postcode = getIntent().getStringExtra(POSTCODE);
        lat = getIntent().getParcelableExtra(POSITION);
        initToolBar(binding.appbar.toolbar, true, postcode + " Dashboard");
        binding.crime.setOnClickListener(this);
        binding.history.setOnClickListener(this);
        queryByPostcode();
    }

    private void chartSetting() {
        binding.historyChart.getDescription().setEnabled(false);
        binding.historyChart.setTouchEnabled(true);
        binding.historyChart.getXAxis().enableGridDashedLine(5f, 5f, 0f);
        binding.historyChart.getAxisLeft().enableGridDashedLine(5f, 5f, 0f);
        for (int i = 0; i < data.size(); i++) {
            Entry entry = new Entry(i, data.get(i).getPrice());
            xLabel.add(data.get(i).getTransferDate());
            values.add(entry);
        }
        binding.historyChart.getXAxis().setLabelCount(3, true);
        binding.historyChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.historyChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index < 0 || index >= xLabel.size()) {
                    return "";
                } else {
                    return xLabel.get((int) value);
                }
            }
        });
        LineDataSet lineDataSet = new LineDataSet(values, "price data");
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(8);
        binding.historyChart.setData(lineData);
        binding.historyChart.invalidate();
    }

    private void queryByPostcode() {
        QueryFactory.init(this);
        Completable.create(emitter -> QueryFactory.awsAppSyncClient().query(QueryByPostcodeQuery.builder().postcode(postcode).build())
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
                        chartSetting();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        Log.e(tag, "query error", e);
                    }
                });
    }

    private GraphQLCall.Callback<QueryByPostcodeQuery.Data> callback(CompletableEmitter completableEmitter) {
        return new GraphQLCall.Callback<QueryByPostcodeQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<QueryByPostcodeQuery.Data> response) {
                if (response.data() == null) {
                    Log.e(tag, "data null");
                    return;
                }
                convertToData(response);
                completableEmitter.onComplete();
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(tag, "fetch error", e);
            }
        };
    }

    private void convertToData(Response<QueryByPostcodeQuery.Data> response) {
        Gson gson = new Gson();
        //........
        QueryByPostcodeQuery.Item item = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(response.data()).queryByPostcode()).items()).get(0);
        for (String model : item.locationPaid()) {
            boolean isRepeat = false;
            PlacePaidData placePaidData = gson.fromJson(model, PlacePaidData.class);
            for (PlacePaidData xData : data) {
                if (placePaidData.getUniqueIdentifier().equals(xData.getUniqueIdentifier())) {
                    isRepeat = true;
                    break;
                }
            }
            if (!isRepeat) {
                data.add(placePaidData);
            }
        }
        sortByDate();
    }

    private void sortByDate() {
        Collections.sort(data, (o1, o2) -> {
            try {
                return Objects.requireNonNull(new SimpleDateFormat("yyyy-MM-dd HH:ss", Locale.ENGLISH)
                        .parse(o1.getTransferDate()))
                        .compareTo(new SimpleDateFormat("yyyy-MM-dd HH:ss", Locale.ENGLISH).parse(o2.getTransferDate()));
            } catch (ParseException e) {
                return 0;
            }
        });
    }


    private void sortByPrice() {
        Collections.sort(data, (o1, o2) -> Integer.compare(o1.getPrice(), o2.getPrice()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.history) {
            HistoryActivity.startHistoryActivity(this, postcode, 1);
        } else if (id == R.id.crime) {
            CrimeHistoryActivity.startCrimeHistoryActivity(this, lat);
        }
    }
}
