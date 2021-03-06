package com.ASETP.project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;


import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.dabase.DaoManager;
import com.ASETP.project.databinding.ActivityMainBinding;
import com.ASETP.project.location.AndroidScheduler;
import com.ASETP.project.location.GoogleMapLocation;
import com.ASETP.project.model.LocationPlaces;
import com.amplifyframework.analytics.UserProfile;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.UserLocation;
import com.amplifyframework.rx.RxAmplify;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.android.heatmaps.HeatmapTileProvider;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author MirageLe, Baptiste Sagna
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> implements OnMapReadyCallback, GoogleMapLocation.OnLocationSuccessListener {

    private GoogleMapLocation mapLocation;

    public static final int REQUEST_CODE = 101;

    private static final int REQUEST_FOR_PLACE = 114;

    private TextView username, lat, lon, currentLocation;

    private LatLng userLocation;

    private TileOverlay overlay;

    private List<Marker> markerList = new ArrayList<>();


    /**
     * radius of earth km
     */
    public static final double EARTH_RADIUS = 6378.137;

    @Override
    protected void init(Bundle bundle) {
        setToolBar();
        setNavHeader();
        mapLocation = new GoogleMapLocation(this, this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        uploadUnsuccessfulGaps();
        recordUser();
    }

    private void recordUser() {
        if (TextUtils.isEmpty(lat.getText().toString())) {
            return;
        }
        UserProfile.Location location = UserProfile.Location.builder()
                .latitude(Double.parseDouble(lat.getText().toString()))
                .longitude(Double.parseDouble(lon.getText().toString()))
                .build();

        UserProfile profile = UserProfile.builder()
                .location(location)
                .email(Amplify.Auth.getCurrentUser().getUsername())
                .build();

        String userId = Amplify.Auth.getCurrentUser().getUserId();

        Amplify.Analytics.identifyUser(userId, profile);
    }

    private void uploadUnsuccessfulGaps() {
        List<UserLocation> data = getSharedPreferences(RxAmplify.Auth.getCurrentUser().getUsername());
        if (data == null || data.size() == 0) {
            return;
        }
        for (UserLocation userLocation : data) {
            uploadPosition(userLocation);
        }
    }

    private void setToolBar() {
        initToolBar(binding.appbar.toolbar, true, "Map");
        binding.appbar.toolbar.inflateMenu(R.menu.toolbar);
        binding.appbar.toolbar.setNavigationIcon(R.mipmap.home_nav);
    }

    private void setNavHeader() {
        View view = binding.navView.getHeaderView(0);
        username = view.findViewById(R.id.username);
        lat = view.findViewById(R.id.lat);
        lon = view.findViewById(R.id.lon);
        currentLocation = view.findViewById(R.id.location);
        binding.navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_call) {
                signOut();
            }
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        } else if (item.getItemId() == R.id.search) {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this);
            startActivityForResult(intent, REQUEST_FOR_PLACE);
        } else {
            setHeatMap();
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapLocation.setGoogleMap(googleMap);
        getLocationPermission();
        mapLocation.updateLocationUi();
        mapLocation.constantGetLocation(locationCallback);
        mapLocation.getGoogleMap().setOnCameraIdleListener(() -> {
            Log.e(tag, "move");
            paringLocationPlace();
        });
        mapLocation.getGoogleMap().setOnMyLocationButtonClickListener(() -> {
            Log.e(tag, "click");
            if (userLocation != null) {
                mapLocation.moveToCamera(userLocation);
                return true;
            }
            return false;
        });
        setOnMarkerListener();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            mapLocation.getCurrentLocation();
        }
    };

    /**
     * check permission if denied go turn on else get location
     */
    private void getLocationPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        boolean checkPermission = checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED;
        if (!checkPermission) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }
    }


    /**
     * when permission return
     *
     * @param requestCode  the code you request
     * @param permissions  the permissions you request
     * @param grantResults return the request result
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            mapLocation.updateLocationUi();
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapLocation.constantGetLocation(locationCallback);
            } else {
                Toast.makeText(this, "Location services needs to be enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_FOR_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                mapLocation.moveToCamera(place.getLatLng());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setTextAndUpload(PlaceLikelihood placeLikelihood) {
        String username = "Email: " + RxAmplify.Auth.getCurrentUser().getUsername();
        this.username.setText(username);
        String latitude = "latitude: " + Objects.requireNonNull(placeLikelihood.getPlace().getLatLng()).latitude;
        lat.setText(latitude);
        String longitude = "longitude: " + placeLikelihood.getPlace().getLatLng().longitude;
        lon.setText(longitude);
        String placeName = "location: " + placeLikelihood.getPlace().getAddress();
        currentLocation.setText(placeName);
        String time = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.UK).format(new Date());
        assert placeLikelihood.getPlace().getLatLng() != null;
        UserLocation userLocation = UserLocation.builder()
                .username(RxAmplify.Auth.getCurrentUser().getUsername())
                .location(placeLikelihood.getPlace().getAddress())
                .latitude((float) placeLikelihood.getPlace().getLatLng().latitude)
                .longitude((float) placeLikelihood.getPlace().getLatLng().longitude)
                .time(time).build();
        uploadPosition(userLocation);
    }

    @Override
    public void onPlace(PlaceLikelihood placeLikelihood) {
        userLocation = placeLikelihood.getPlace().getLatLng();
        setTextAndUpload(placeLikelihood);
    }

    private void paringLocationPlace() {
        mapLocation.getGoogleMap().clear();
        LatLngBounds bounds = mapLocation.getGoogleMap().getProjection().getVisibleRegion().latLngBounds;
        double minLat = bounds.southwest.latitude;
        double minLong = bounds.southwest.longitude;
        double maxLat = bounds.northeast.latitude;
        double maxLong = bounds.northeast.longitude;
        List<LocationPlaces> locationPlaces;
        List<LatLng> latLngs = new ArrayList<>();
        locationPlaces = DaoManager.getLocationInstance(this).searchLocationPlacesWithPosition(minLat, maxLat, minLong, maxLong);
        markerList = setMarker(locationPlaces);
        for (LocationPlaces locationPlaces1 : locationPlaces) {
            latLngs.add(new LatLng(locationPlaces1.getLatitude(), locationPlaces1.getLongitude()));
        }
        addHeatMap(latLngs);
    }

    private void addHeatMap(List<LatLng> latLngs) {
        if (latLngs.size() <= 0) {
            return;
        }
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder().data(latLngs).build();
        overlay = mapLocation.getGoogleMap().addTileOverlay(new TileOverlayOptions().tileProvider(provider));
        overlay.setVisible(false);
    }

    private void setHeatMap() {
        if (overlay.isVisible()) {
            for (Marker marker : markerList) {
                marker.setVisible(true);
            }
            overlay.setVisible(false);
        } else {
            for (Marker marker : markerList) {
                marker.setVisible(false);
            }
            overlay.setVisible(true);
        }
    }

    private void getWholeLocationPaidData(String postcode, LatLng position) {
        DashboardActivity.startDashboardActivity(this, postcode, position);
    }

    private List<Marker> setMarker(List<LocationPlaces> locationPlaces) {
        List<Marker> markers = new ArrayList<>();
        for (LocationPlaces locationPlaces1 : locationPlaces) {
            markers.add(mapLocation.addLocationPlaceMarker(locationPlaces1));
        }
        return markers;
    }

    @SuppressLint("PotentialBehaviorOverride")
    private void setOnMarkerListener() {
        mapLocation.getGoogleMap().setOnMarkerClickListener(marker -> {
            getWholeLocationPaidData(marker.getTitle(), marker.getPosition());
            return true;
        });
    }


    private void uploadPosition(UserLocation userLocation) {
        /*
         * If you have multiple endpoints defined, as above, but you only intend to use one,
         * then simply remove the one you don't need.
         * If you have multiple endpoints defined, and intend to use more than just one of them,
         * you need to specify the name of the API when you perform the mutate call:
         */
        RxAmplify.API.mutate("softwareengproject", ModelMutation.create(userLocation))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new SingleObserver<GraphQLResponse<UserLocation>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull GraphQLResponse<UserLocation> userLocationGraphResponse) {
                        Log.e(tag, userLocationGraphResponse.toString());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(tag, "update gps error", e);
                        saveSharedPreferences(RxAmplify.Auth.getCurrentUser().getUsername(), userLocation);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void signOut() {
        Log.e(tag, "logout");
        RxAmplify.Auth.signOut().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Log.e(tag, "sign out success");
                finish();
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.e(tag, "signOut error", e);
            }
        });
    }
}
