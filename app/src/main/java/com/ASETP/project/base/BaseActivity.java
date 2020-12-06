package com.ASETP.project.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.ASETP.project.R;
import com.amplifyframework.datastore.generated.model.UserLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author MirageLe
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    public T binding;

    protected String tag;

    private Dialog loadingDialog;

    private SharedPreferences preferences;

    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = this.getClass().getSimpleName();
        Type superclass = this.getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            try {
                Method method = aClass.getMethod("inflate", LayoutInflater.class);
                binding = (T) method.invoke(null, getLayoutInflater());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Log.e(tag, e.toString());
            }
        }
        setContentView(binding.getRoot());
        preferences = this.getSharedPreferences("softWareEngProject", Activity.MODE_PRIVATE);
        init(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * setup toolbar
     *
     * @param toolbar        toolbar
     * @param homeAsUpEnable isHaveBackButton
     * @param title          title
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnable, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(homeAsUpEnable);
    }

    /**
     * 创建dialog
     *
     * @param context context
     * @param msg     message
     * @return waiting dialog
     */
    private static Dialog createWaiteDialog(Context context, String msg) {

        View view = LayoutInflater.from(context).inflate(R.layout.widget_wait_dialog, null);

        LinearLayout layout = view.findViewById(R.id.dialog_view);

        ImageView circleImg = view.findViewById(R.id.img);

        TextView tip = view.findViewById(R.id.tipTextView);

        //loadAnimation
        Animation rotateAnim = AnimationUtils.loadAnimation(context, R.anim.progress_loading);

        //startAnimation
        circleImg.startAnimation(rotateAnim);

        //setText
        tip.setText(msg);

        //CustomProgressDialog
        Dialog loadingDialog = new Dialog(context, R.style.CustomProgressDialog);

        //can't cancel until loading finished
        loadingDialog.setCancelable(false);

        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        return loadingDialog;
    }

    public void showWaitDialog(String message) {
        if (loadingDialog == null) {
            loadingDialog = createWaiteDialog(this, message);
        }
        loadingDialog.show();
    }

    public void hideWaitDialog() {
        if (loadingDialog != null) {
            try {
                loadingDialog.dismiss();
                loadingDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void addSubscription(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    protected void unSubscription() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    protected boolean checkIfInit() {
        return preferences.getBoolean("init", false);
    }

    protected void setInitSuccess() {
        preferences.edit().putBoolean("init", true).apply();
    }

    protected void clear(String name) {
        preferences.edit().putString(name, null).apply();
    }

    protected void saveSharedPreferences(String name, UserLocation userLocation) {
        Gson gson = new Gson();
        String leftData = preferences.getString(name, null);
        List<UserLocation> data = new ArrayList<>();
        if (leftData != null) {
            data = gson.fromJson(leftData, new TypeToken<List<UserLocation>>() {
            }.getType());
        }
        data.add(userLocation);
        preferences.edit().putString(name, gson.toJson(data)).apply();
    }

    protected void cleanSp(String name) {
        preferences.edit().clear().apply();
    }

    protected List<UserLocation> getSharedPreferences(String name) {
        List<UserLocation> data;
        String leftData = preferences.getString(name, null);
        if (leftData == null) {
            return null;
        }
        Gson gson = new Gson();
        data = gson.fromJson(leftData, new TypeToken<List<UserLocation>>() {
        }.getType());
        cleanSp(name);
        return data;
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * First method to run
     *
     * @param bundle savedInstanceState
     */
    protected abstract void init(Bundle bundle);

}
