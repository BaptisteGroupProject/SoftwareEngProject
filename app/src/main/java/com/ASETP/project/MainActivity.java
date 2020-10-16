package com.ASETP.project;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityMainBinding;

/**
 * @author MirageLe
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        binding.test.setText("test");
    }

}
