package com.ASETP.project;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

/**
 * @author MirageLee, SumedhN
 * @date 2020/10/29
 */
public class AmplifyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("LoginActivity", "Initialized Amplify at Login");
            Amplify.Auth.fetchAuthSession(
                    result -> Log.i("AmplifyQuickstart", result.toString()),
                    error -> Log.e("AmplifyQuickstart", error.toString())
            );
        } catch (AmplifyException error) {
            Log.e("LoginActivity", "Could not initialize Amplify at Login", error);
        }
    }
}
