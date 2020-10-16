package com.ASETP.project.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    protected T binding;

    protected String tag;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class,ViewGroup.class,boolean.class);
            binding = (T) method.invoke(null, getLayoutInflater(),container,false);
        } catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
            Log.e(tag,e.toString());
        }
        return binding.getRoot();
    }

    /**
     * First method in fragment
     */
    protected abstract void init();
}
