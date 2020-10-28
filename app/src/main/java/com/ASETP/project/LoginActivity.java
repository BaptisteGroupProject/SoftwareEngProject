package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    private EditText Username;
    private EditText Password;
    private TextView info;
    private Button Signin;
    private TextView Signup;
    private int counter = 4;

    private String userName = "Admin";
    private String passWord = "12345";

    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = (EditText) findViewById(R.id.inputEmail);
        Password = (EditText) findViewById(R.id.inputPassword);
       // info = (TextView) findViewById(R.id.textView);
        Signin = (Button) findViewById(R.id.btnRegister);
        Signup = (TextView) findViewById(R.id.goToRegister);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputName = Username.getText().toString();
                String inputPassword = Password.getText().toString();

                if (inputName.isEmpty() || inputPassword.isEmpty()){

                    Toast.makeText(LoginActivity.this, "please enter all the details correctly" , Toast.LENGTH_SHORT).show();

                }
                else {
                    isValid = validate(inputName, inputPassword);

                    if(!isValid){
                        counter --;
                        Toast.makeText(LoginActivity.this, "Credentials incorrect" , Toast.LENGTH_SHORT).show();

                        if(counter == 0) {
                            Signin.setEnabled(false);
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

              }
            }
        });


      /*////*  final Intent switchToLogin = new Intent(this, MainActivity.class);
        Button loginButton = findViewById(R.id.btnRegister);
        // Make the login button receptive to being clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            // onClick method implementation for the killButton object
            public void onClick(View v) {
                startActivity(switchToLogin);
            }
        });*///

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

    private boolean validate(String name, String password) {

        return name.equals(userName) && password.equals(passWord);
    }

}
