package com.ASETP.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements TextWatcher {

    @Override
    protected void init(Bundle bundle) {
        binding.inputEmail.addTextChangedListener(this);
        binding.inputPassword.addTextChangedListener(this);
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = binding.inputEmail.getText().toString();
                String inputPassword = binding.inputPassword.getText().toString();
                if (!validateEmail(inputEmail)) {
                    showToast("Please enter a valid email");
                } else {
                    final Intent switchBackToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(switchBackToLogin);
                    finish();
                    showToast("Register success");
                }
            }
        });
    }

    private boolean validateEmail(String inputEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
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
            binding.btnRegister.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_round_bg));
        } else {
            binding.btnRegister.setBackground(ContextCompat.getDrawable(this, R.drawable.gray_round_bg));
        }
        binding.btnRegister.setEnabled(isLoginEnable);
    }
}






