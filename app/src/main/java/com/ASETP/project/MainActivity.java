package com.ASETP.project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityMainBinding;

/**
 * @author MirageLe
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private boolean isPermissionOk = false;

    @Override
    protected void init(Bundle bundle) {

    }

    /**
     * check permission if denied go turn on else get location
     */
    private void getPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION};
        boolean checkPermission = checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(permissions[1]) == PackageManager.PERMISSION_GRANTED;
        if (!checkPermission) {
            ActivityCompat.requestPermissions(this, permissions, 1);
            isPermissionOk = false;
        } else {
            isPermissionOk = true;
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
        if (requestCode == 1) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    isPermissionOk = false;
                    Toast.makeText(MainActivity.this, "please turn on location service", Toast.LENGTH_SHORT).show();
                    return;
                }
                isPermissionOk = true;
            }
        }
    }
}
