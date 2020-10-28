package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Intent switchToLogin = new Intent(this, MainActivity.class);
        Button loginButton = findViewById(R.id.btnRegister);
        // Make the login button receptive to being clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            // onClick method implementation for the killButton object
            public void onClick(View v) {
                startActivity(switchToLogin);

            }
        });

        final Intent switchToRegister = new Intent(this, RegisterActivity.class);
        TextView registerButton = (TextView) findViewById(R.id.goToRegister);
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        startActivity(switchToRegister);
                    }
                }
        );

    }
}
