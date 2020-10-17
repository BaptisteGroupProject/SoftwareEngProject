package com.ASETP.project.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Process;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

/**
 * on 2020/10/16
 *
 * @author MirageLe
 */
public class Gps {

    private Location location;

    private final Context context;

    private final LocationListener listener;

    public Gps(Context context, LocationListener locationListener) {
        this.context = context;
        this.listener = locationListener;
    }

    /**
     * check the permission if granted start getting location else make a toast
     */
    public void getGps() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String contextText = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) context.getSystemService(contextText);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            setLocation(location);
            if (listener != null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, listener);
            }
        } else {
            Toast.makeText(context, "please turn on location service", Toast.LENGTH_SHORT).show();
        }
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
