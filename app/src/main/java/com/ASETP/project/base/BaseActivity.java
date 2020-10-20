package com.ASETP.project.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author MirageLe
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    public T binding;

    protected String tag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = this.getClass().getSimpleName();
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            try {
                Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
                binding = (T) method.invoke(null,getLayoutInflater());
            } catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
                Log.e(tag,e.toString());
            }
        }
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    /**
     * First method to run
     */
    protected abstract void init(Bundle bundle);
}
