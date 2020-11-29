package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.dabase.DaoManager;
import com.ASETP.project.databinding.ActivityLoadingBinding;
import com.ASETP.project.utils.FileUtils;
import com.amplifyframework.core.Amplify;

/**
 * @author Mirage
 */
public class LoadingActivity extends BaseActivity<ActivityLoadingBinding> {

    @Override
    protected void init(Bundle bundle) {
        Log.e(tag, "is init:" + checkIfInit());
        DaoManager.getInstance(this);
        if (!checkIfInit()) {
            FileUtils fileUtils = new FileUtils(this);
            binding.progress.setMax(100);
            fileUtils.setOnReadingListener(new FileUtils.OnReadingListener() {
                @Override
                public void onReading(int i) {
                    binding.progress.setProgress(i);
                }

                @Override
                public void onSuccess() {
                    setInitSuccess();
                    startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                }
            });
            fileUtils.readPostcodeCSV();
        } else if (Amplify.Auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}