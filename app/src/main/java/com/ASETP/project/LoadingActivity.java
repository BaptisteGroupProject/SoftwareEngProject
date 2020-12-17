package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityLoadingBinding;
import com.ASETP.project.utils.FileUtils;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amplifyframework.core.Amplify;


/**
 * @author Mirage
 */
public class LoadingActivity extends BaseActivity<ActivityLoadingBinding> {

    @Override
    protected void init(Bundle bundle) {
        binding.progress.setMax(100);
        FileUtils fileUtils = new FileUtils(this);
        if (fileUtils.isFileExist()) {
            if (Amplify.Auth.getCurrentUser() != null) {
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
            }
            finish();
        } else {
            TransferUtility transferUtility;
            transferUtility = TransferUtility.builder().context(this)
                    .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                    .s3Client(new AmazonS3Client(new BasicAWSCredentials(getString(R.string.accessKey), getString(R.string.secret_key)), Region.getRegion(Regions.EU_WEST_1))).build();

            TransferObserver transferObserver = transferUtility
                    .download("location", fileUtils.getLocationDbFile());
            transferObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (state == TransferState.COMPLETED) {
                        startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                        finish();
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    Log.e(tag, bytesCurrent + ":" + bytesTotal);
                    double i = 100 * (double) bytesCurrent / (double) bytesTotal;
                    binding.progress.setProgress((int) i);
                }

                @Override
                public void onError(int id, Exception ex) {
                    Log.e(tag, "error", ex);
                }
            });
        }
    }
}