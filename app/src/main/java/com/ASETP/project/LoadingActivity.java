package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityLoadingBinding;
import com.ASETP.project.utils.FileUtils;
import com.amplifyframework.core.Amplify;

/**
 * @author Mirage
 */
public class LoadingActivity extends BaseActivity<ActivityLoadingBinding> {

    @Override
    protected void init(Bundle bundle) {
//        binding.progress.setMax(100);
//        FileUtils fileUtils = new FileUtils(this);
//        fileUtils.setOnReadingListener(new FileUtils.OnReadingListener() {
//            @Override
//            public void onReading(int i) {
//                binding.progress.setProgress(i);
//            }
//
//            @Override
//            public void onSuccess() {
//
//            }
//        });
//        fileUtils.uploadCrimeData();
        if (Amplify.Auth.getCurrentUser() != null) {
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
            finish();
        }
    }
}