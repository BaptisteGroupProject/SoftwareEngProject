package com.ASETP.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ASETP.project.adapter.HistoryAdapter;
import com.ASETP.project.base.BaseActivity;
import com.ASETP.project.dabase.DaoManager;
import com.ASETP.project.databinding.ActivityHistoryBinding;
import com.ASETP.project.location.AndroidScheduler;
import com.ASETP.project.model.PlacePaidData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author Mirage
 * @date 2020/11/20
 */
public class HistoryActivity extends BaseActivity<ActivityHistoryBinding> {

    public static String DATA_KEY = "data";

    public static String POSTCODE = "postcode";

    private List<PlacePaidData> data = new ArrayList<>();

    private String postcode;

    private HistoryAdapter adapter;

    public static void startHistoryActivity(Context context, String postcode) {
        Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra(POSTCODE, postcode);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle bundle) {
        postcode = getIntent().getStringExtra(POSTCODE);
        initToolBar(binding.appbar.toolbar, true, postcode + " History");
        getData();
    }

    private void getData() {
        Observable.create((ObservableOnSubscribe<List<PlacePaidData>>) emitter -> {
            Log.e(tag,postcode);
            List<PlacePaidData> data = DaoManager
                    .getInstance(HistoryActivity.this, String.valueOf(postcode.charAt(0)))
                    .searchPlacePaidData(postcode);
            emitter.onNext(data);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidScheduler.mainThread())
                .subscribe(new Observer<List<PlacePaidData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("loading....");
                        Log.e(tag, "start searching");
                    }

                    @Override
                    public void onNext(@NonNull List<PlacePaidData> placePaidData) {
                        data = placePaidData;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        Log.e(tag, "get data error", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(tag, "complete search");
                        hideWaitDialog();
                        if (data == null || data.size() == 0) {
                            binding.noData.setVisibility(View.VISIBLE);
                            return;
                        }
                        adapter = new HistoryAdapter(HistoryActivity.this, data);
                        binding.list.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                        binding.list.setAdapter(adapter);
                    }
                });
    }
}