package com.ASETP.project.utils;

import android.content.Context;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.userpools.CognitoUserPoolsSignInProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicAPIKeyAuthProvider;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider;
import com.amazonaws.mobileconnectors.appsync.sigv4.CognitoUserPoolsAuthProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;

/**
 * @author MirageLee
 * @date 2020/12/4
 */
public class QueryFactory {
    private static volatile AWSAppSyncClient client;

    private static String apiKey = "da2-ex46ehugendcrnosytt4gxnddy";

    public static synchronized void init(Context context) {
        if (client == null) {
            AWSConfiguration awsConfiguration = new AWSConfiguration(context);
            client = AWSAppSyncClient.builder()
                    .context(context)
                    .apiKey(new BasicAPIKeyAuthProvider(apiKey))
                    .awsConfiguration(awsConfiguration)
                    .build();
        }
    }

    public static synchronized AWSAppSyncClient awsAppSyncClient() {
        return client;
    }
}
