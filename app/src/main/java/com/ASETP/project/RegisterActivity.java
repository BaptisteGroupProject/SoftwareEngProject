package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Intent switchBackToLogin = new Intent(this, LoginActivity.class);
        Button registerButton = findViewById(R.id.btnLogin);
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        startActivity(switchBackToLogin);
                    }
                }
        );
    }
}
