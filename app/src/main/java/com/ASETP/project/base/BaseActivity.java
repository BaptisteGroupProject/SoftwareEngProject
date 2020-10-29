package com.ASETP.project.base;

import android.app.Dialog;
import android.content.Context;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author MirageLe
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    public T binding;

    protected String tag;

    private Dialog loadingDialog;

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

    protected void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * First method to run
     *
     * @param bundle savedInstanceState
     */
    protected abstract void init(Bundle bundle);

}
