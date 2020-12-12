package com.ASETP.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityDashboardBinding;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Owner
 */
public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> implements View.OnClickListener {

    private String postcode;

    public static String POSTCODE = "postcode";

    public static String POSITION = "lat";

    private LatLng lat;

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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.history) {
            HistoryActivity.startHistoryActivity(this, postcode,1);
        } else if (id == R.id.crime) {
            CrimeHistoryActivity.startCrimeHistoryActivity(this, lat);
        }
    }
}
