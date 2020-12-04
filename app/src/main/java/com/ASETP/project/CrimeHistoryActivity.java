package com.ASETP.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityHistoryBinding;

public class CrimeHistoryActivity extends BaseActivity<ActivityHistoryBinding> {

    public static String POSTCODE = "postcode";

    private String postcode;

    public static void startCrimeHistoryActivity(Context context, String postcode) {
        Intent intent = new Intent(context, CrimeHistoryActivity.class);
        intent.putExtra(POSTCODE, postcode);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle bundle) {
        postcode = getIntent().getStringExtra(POSTCODE);

    }
}
