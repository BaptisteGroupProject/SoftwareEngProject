package com.ASETP.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityRegisterBinding;
import com.ASETP.project.location.AndroidScheduler;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.rx.RxAmplify;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements TextWatcher {

    @Override
    protected void init(Bundle bundle) {
        binding.inputEmail.addTextChangedListener(this);
        binding.inputPassword.addTextChangedListener(this);
        binding.btnRegister.setOnClickListener(v -> {
            String inputEmail = binding.inputEmail.getText().toString();
            String inputPassword = binding.inputPassword.getText().toString();
            if (!validateEmail(inputEmail)) {
                showToast("Please enter a valid email");
            } else {
                register(inputEmail, inputPassword);
            }
        });
    }

    private void register(String email, String password) {
        RxAmplify.Auth.signUp(
                email,
                password,
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new SingleObserver<AuthSignUpResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("");
                    }

                    @Override
                    public void onSuccess(@NonNull AuthSignUpResult authSignUpResult) {
                        hideWaitDialog();
                        showToast("An Email had been send");
                        Log.e(tag, authSignUpResult.toString());
                        showDialog(email);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        Log.e(tag, "register error", e);
                    }
                });
    }

    private void showDialog(String username) {
        EditText editText = new EditText(this);
        editText.setBackground(null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(editText)
                .setTitle("Confirm Email")
                .setPositiveButton("OK", (dialog, which) -> {
                    String code = editText.getText().toString();
                    confirmEmail(username, code);
                }).setNegativeButton("cancel", (dialog, which) -> {

                }).setCancelable(true);
        builder.create().show();
    }


    private void confirmEmail(String username, String code) {
        RxAmplify.Auth.confirmSignUp(username, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new SingleObserver<AuthSignUpResult>() {
                               @Override
                               public void onSubscribe(@NonNull Disposable d) {
                                   showWaitDialog("");
                               }

                               @Override
                               public void onSuccess(@NonNull AuthSignUpResult authSignUpResult) {
                                   final Intent switchBackToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                   Log.e(tag, authSignUpResult.toString());
                                   showToast("Sign up success");
                                   startActivity(switchBackToLogin);
                                   hideWaitDialog();
                                   finish();
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   Log.e(tag, "confirmError", e);
                                   showToast(e.getMessage());
                                   hideWaitDialog();
                               }
                           }
                );
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






