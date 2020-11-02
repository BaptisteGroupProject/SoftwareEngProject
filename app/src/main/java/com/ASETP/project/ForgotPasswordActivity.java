package com.ASETP.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityForgotPasswordBinding;
import com.ASETP.project.location.AndroidScheduler;
import com.amplifyframework.auth.result.AuthResetPasswordResult;
import com.amplifyframework.rx.RxAmplify;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding> implements TextWatcher {

    @Override
    protected void init(Bundle bundle) {
        binding.inputEmail.addTextChangedListener(this);
        binding.inputPassword1.addTextChangedListener(this);
        binding.inputPassword2.addTextChangedListener(this);
        binding.btnChangePassword.setOnClickListener(v -> {
            String inputEmail = binding.inputEmail.getText().toString();
            String password1 = binding.inputPassword1.getText().toString();
            String password2 = binding.inputPassword2.getText().toString();
            if (!validateEmail(inputEmail)) {
                showToast("Please enter a valid email");
            } else if (!password1.matches(password2)){
                showToast("Passwords do not match");
            } else {
                changePassword(inputEmail, password1);
            }
        });
    }

    private void changePassword(String email, String newPassword){
        RxAmplify.Auth.resetPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new SingleObserver<AuthResetPasswordResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("");
                    }

                    @Override
                    public void onSuccess(@NonNull AuthResetPasswordResult authResetPasswordResult) {
                        hideWaitDialog();
                        showToast("Confirmation code been sent to your email");
                        Log.e(tag, authResetPasswordResult.toString());
                        showDialog(newPassword);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        showToast(e.getMessage());
                        Log.e(tag, "register error", e);
                    }
                });
    }

    private void showDialog(String newPassword) {
        EditText editText = new EditText(this);
        editText.setBackground(null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(editText)
                .setTitle("Confirm Password")
                .setPositiveButton("OK", (dialog, which) -> {
                    String code = editText.getText().toString();
                    confirmNewPassword(code, newPassword);
                }).setNegativeButton("cancel", (dialog, which) -> {

                }).setCancelable(true);
        builder.create().show();
    }

    private void confirmNewPassword(String code, String newPassword){
        RxAmplify.Auth.confirmResetPassword(newPassword, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("");
                    }

                    @Override
                    public void onComplete() {
                        final Intent switchBackToLogin = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                        showToast("Password change complete");
                        startActivity(switchBackToLogin);
                        hideWaitDialog();
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(tag, "Confirm new password error", e);
                        showToast(e.getMessage());
                        hideWaitDialog();
                    }
                });
    }

    private boolean validateEmail(String inputEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        setSubmitButton(binding.inputEmail.getText().length() > 0
                && binding.inputPassword1.getText().length() > 0
                && binding.inputPassword2.getText().length() > 0);
    }

    private void setSubmitButton(boolean isLoginEnable) {
        if (isLoginEnable) {
            binding.btnChangePassword.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_round_bg));
        } else {
            binding.btnChangePassword.setBackground(ContextCompat.getDrawable(this, R.drawable.gray_round_bg));
        }
        binding.btnChangePassword.setEnabled(isLoginEnable);
    }


}
