package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements View.OnClickListener, TextWatcher {

    private int maxLoginAttempts = 4;

    /**
     * Temp login details
     */
    private final String userName = "Admin@email.com";
    private final String passWord = "12345";


    @Override
    protected void init(Bundle bundle) {
        generalSetting();
    }

    private void generalSetting() {
        binding.inputEmail.addTextChangedListener(this);
        binding.inputPassword.addTextChangedListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.forgotPassword.setOnClickListener(this);
        binding.goToRegister.setOnClickListener(this);
    }

    private boolean validate(String name, String password) {
        return name.equals(userName) && password.equals(passWord);
    }

    private boolean validateEmail(String inputEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnLogin) {
            String inputEmail = binding.inputEmail.getText().toString();
            String inputPassword = binding.inputPassword.getText().toString();
            if (!validateEmail(inputEmail)) {
                showToast("Please enter a valid email");
            } else {
                if (!validate(inputEmail, inputPassword)) {
                    maxLoginAttempts--;
                    showToast("Credentials Incorrect");
                    if (maxLoginAttempts == 0) {
                        setSubmitButton(false);
                    }
                } else {
                    showToast("Login Successful");
                    Intent beginLogin = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(beginLogin);
                }
            }
        } else if (id == R.id.forgotPassword) {

        } else {
            Intent switchToRegister = new Intent(this, RegisterActivity.class);
            startActivity(switchToRegister);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setSubmitButton(binding.inputEmail.getText().length() > 0 && binding.inputPassword.getText().length() > 0);
    }

    /**
     * check if loginButton is enable
     *
     * @param isLoginEnable isLoginEnable
     */
    private void setSubmitButton(boolean isLoginEnable) {
        if (isLoginEnable) {
            binding.btnLogin.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_round_bg));
        } else {
            binding.btnLogin.setBackground(ContextCompat.getDrawable(this, R.drawable.gray_round_bg));
        }
        binding.btnLogin.setEnabled(isLoginEnable);
    }
}
