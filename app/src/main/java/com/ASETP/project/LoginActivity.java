package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private int maxLoginAttempts = 4;

    //Temp login details
    private String userName = "Admin";
    private String passWord = "12345";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText Username = findViewById(R.id.inputEmail);
        final EditText Password = findViewById(R.id.inputPassword);
        final Button loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputEmail = Username.getText().toString();
                String inputPassword = Password.getText().toString();

                if (inputEmail.isEmpty() || inputPassword.isEmpty()){

                    Toast.makeText(LoginActivity.this, "Please enter all the details correctly" , Toast.LENGTH_SHORT).show();
                }
                else if(!validateEmail(inputEmail)) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid email" , Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!validate(inputEmail, inputPassword)){
                        maxLoginAttempts --;
                        Toast.makeText(LoginActivity.this, "Credentials Incorrect" , Toast.LENGTH_SHORT).show();

                        if(maxLoginAttempts == 0) {
                            loginButton.setEnabled(false);
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        Intent beginLogin = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(beginLogin);
                    }
              }
            }
        });

        final Intent switchToRegister = new Intent(this, RegisterActivity.class);
        final TextView registerButton = findViewById(R.id.goToRegister);
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        startActivity(switchToRegister);
                    }
                }
        );

    }

    private boolean validate(String name, String password) {

        return name.equals(userName) && password.equals(passWord);
    }

    private boolean validateEmail(String inputEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
    }

}
