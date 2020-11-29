package com.ASETP.project.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.ASETP.project.R;
import com.ASETP.project.model.LocationPlaces;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

/**
 * @author MirageLee
 * @date 2020/10/20
 */
public class GoogleMapLocation {
    private GoogleMap googleMap;

    private final Context context;

    private Location lastKnownLocation;
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final PlacesClient placesClient;

    private static final int DEFAULT_ZOOM = 17;

    OnLocationSuccessListener onLocationSuccessListener;

    private LocationRequest locationRequest;

    private boolean isFirstSearch = true;

    public GoogleMapLocation(Context context, OnLocationSuccessListener onLocationSuccessListener) {
        this.context = context;
        this.onLocationSuccessListener = onLocationSuccessListener;
        Places.initialize(this.context, this.context.getString(R.string.google_api));
        placesClient = Places.createClient(this.context);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.context);
        createLocationRequest();
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void updateLocationUi() {
        if (googleMap == null) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            googleMap.setMyLocationEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void createLocationRequest() {
        int five = 1000 * 60 * 5;
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(five);
        locationRequest.setFastestInterval(five);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void constantGetLocation(LocationCallback locationCallback) {
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void getCurrentLocation() {
        if (googleMap == null) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG, Place.Field.PLUS_CODE);
        // Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<FindCurrentPlaceResponse> placeResult = placesClient.findCurrentPlace(request);
            placeResult.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
                @Override
                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        FindCurrentPlaceResponse likelyPlaces = task.getResult();
                        onLocationSuccessListener.onPlace(likelyPlaces.getPlaceLikelihoods().get(0));
                        if (isFirstSearch) {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    likelyPlaces.getPlaceLikelihoods().get(0).getPlace().getLatLng(), DEFAULT_ZOOM));
                            isFirstSearch = false;
                        }
                    } else {
                        Log.e(this.getClass().getSimpleName(), "Exception: %s", task.getException());
                    }
                }
            });
        }
    }

    public void addLocationPlaceMarker(LocationPlaces locationPlaces) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(locationPlaces.getLatitude(), locationPlaces.getLongitude()))
                .title(locationPlaces.getPostcode()));
    }

    public interface OnLocationSuccessListener {
        /**
         * when search place success
         *
         * @param placeLikelihood the position that likely to be
         */
        void onPlace(PlaceLikelihood placeLikelihood);
    }
}
