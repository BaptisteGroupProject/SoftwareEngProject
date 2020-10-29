package com.ASETP.project;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;

/**
 * @author MirageLee
 * @date 2020/10/29
 */
public class AmplifyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Amplify.configure(getApplicationContext());
            Log.i("LoginActivity", "Initialized Amplify at Login");
        } catch (AmplifyException error) {
            Log.e("LoginActivity", "Could not initialize Amplify at Login", error);
        }
    }
}
