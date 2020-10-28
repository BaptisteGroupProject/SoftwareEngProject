package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText Username = findViewById(R.id.inputEmail);
        final EditText Password = findViewById(R.id.inputPassword);

        final Intent switchBackToLogin = new Intent(this, LoginActivity.class);
        Button registerButton = findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        String inputEmail = Username.getText().toString();
                        String inputPassword = Password.getText().toString();

                        if (inputEmail.isEmpty() || inputPassword.isEmpty()){
                            Toast.makeText(RegisterActivity.this, "Please enter information in all fields" , Toast.LENGTH_SHORT).show();
                        } else if (!validateEmail(inputEmail)) {
                            Toast.makeText(RegisterActivity.this, "Please enter a valid email" , Toast.LENGTH_SHORT).show();
                        } else{
                            startActivity(switchBackToLogin);
                        }
                    }
                }
        );
    }

    private boolean validateEmail(String inputEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
    }
}






