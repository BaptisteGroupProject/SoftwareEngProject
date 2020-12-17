package com.ASETP.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.databinding.ActivityLoginBinding;
import com.ASETP.project.location.AndroidScheduler;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.rx.RxAmplify;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * @author Mirage
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements View.OnClickListener, TextWatcher {

    private int maxLoginAttempts = 4;

    private final String errorMsg = "User not confirmed in the system.";

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

    private void validate(String name, String password) {
        RxAmplify.Auth.signIn(name, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread()).subscribe(new SingleObserver<AuthSignInResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                showWaitDialog("");
            }

            @Override
            public void onSuccess(@NonNull AuthSignInResult authSignInResult) {
                if (authSignInResult.isSignInComplete()) {
                    hideWaitDialog();
                    showToast("sign in complete");
                    Log.e(tag, authSignInResult.toString());
                    Intent beginLogin = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(beginLogin);
                    finish();
                } else {
                    maxLoginAttempts--;
                    showToast("Credentials Incorrect");
                    if (maxLoginAttempts == 0) {
                        setSubmitButton(false);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(tag, "login error", e);
                if (e.getMessage().equals(errorMsg)) {
                    Log.e(tag, "111");
                    showDialog(name, "Confirm Email");
                }
                showToast(e.getMessage());
                hideWaitDialog();
            }
        });
    }

    private void showDialog(String username, String title) {
        EditText editText = new EditText(this);
        editText.setBackground(null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(editText)
                .setTitle(title)
                .setPositiveButton("OK", (dialog, which) -> {
                    String code = editText.getText().toString();
                    confirmEmail(username, code);
                }).setNegativeButton("RESENT", (dialog, which) -> resent(username)).setCancelable(true);
        builder.create().show();
    }

    private void resent(String username) {
        RxAmplify.Auth.resendSignUpCode(username).subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread()).subscribe(new SingleObserver<AuthSignUpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                showWaitDialog("");
            }

            @Override
            public void onSuccess(@NonNull AuthSignUpResult authSignUpResult) {
                hideWaitDialog();
                showDialog(username, "Confirm Email");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                hideWaitDialog();
                Log.e(tag, "resent error", e);
                showToast(e.getMessage());
            }
        });
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
                                   Log.e(tag, authSignUpResult.toString());
                                   String inputPassword = binding.inputPassword.getText().toString();
                                   validate(username, inputPassword);
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   Log.e(tag, "confirmError", e);
                                   showToast(e.getMessage());
                                   hideWaitDialog();
                                   showDialog(username, "Code error, please try again");
                               }
                           }
                );
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
            } else if (inputPassword.length() < 8) {
                showToast("Password must be at least 8 characters");
            } else {
                validate(inputEmail, inputPassword);
            }
        } else if (id == R.id.forgotPassword) {
            Intent switchToForgotPassword = new Intent(this, ForgotPasswordActivity.class);
            startActivity(switchToForgotPassword);
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
