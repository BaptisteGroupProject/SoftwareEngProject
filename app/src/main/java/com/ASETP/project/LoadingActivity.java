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
        if (Amplify.Auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}