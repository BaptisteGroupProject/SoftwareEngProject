package com.ASETP.project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;


import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityMainBinding;
import com.ASETP.project.location.GoogleMapLocation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.model.PlaceLikelihood;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author MirageLe, Baptiste Sagna
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> implements OnMapReadyCallback, GoogleMapLocation.OnLocationSuccessListener {

    private GoogleMapLocation mapLocation;

    private static final int REQUEST_CODE = 101;

    private CircleImageView icon;
    private TextView username, lat, lon, currentLocation;

    @Override
    protected void init(Bundle bundle) {
        setToolBar();
        setNavHeader();
        mapLocation = new GoogleMapLocation(this, this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setToolBar() {
        initToolBar(binding.appbar.toolbar, true, "Map");
        binding.appbar.toolbar.setNavigationIcon(R.mipmap.home_nav);
    }

    private void setNavHeader() {
        View view = binding.navView.getHeaderView(0);
        icon = view.findViewById(R.id.icon_image);
        username = view.findViewById(R.id.username);
        lat = view.findViewById(R.id.lat);
        lon = view.findViewById(R.id.lon);
        currentLocation = view.findViewById(R.id.location);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapLocation.setGoogleMap(googleMap);
        getLocationPermission();
        mapLocation.updateLocationUi();
        mapLocation.getCurrentLocation();
    }

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
                mapLocation.getCurrentLocation();
            } else {
                Toast.makeText(this, "Location services needs to be enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPlace(PlaceLikelihood placeLikelihood) {
        String latitude = "latitude: " + placeLikelihood.getPlace().getLatLng().latitude;
        lat.setText(latitude);
        String longitude = "longitude: " + placeLikelihood.getPlace().getLatLng().longitude;
        lon.setText(longitude);
        String placeName = "location: " + placeLikelihood.getPlace().getAddress();
        currentLocation.setText(placeName);
    }
}
