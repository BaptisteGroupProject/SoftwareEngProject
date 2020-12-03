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
import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelPagination;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.datastore.generated.model.PricePaidJson;
import com.amplifyframework.rx.RxAmplify;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

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
//        getData();
        setTest();
    }
    int i = 0;
    private void setTest() {
        BehaviorSubject<GraphQLRequest<PaginatedResult<PricePaidJson>>> subject =
                BehaviorSubject.createDefault(ModelQuery.list(PricePaidJson.class,
                        PricePaidJson.POSTCODE.eq(postcode),
                        ModelPagination.limit(100000)));

        subject.concatMap(paginatedResultGraphQLRequest -> RxAmplify.API.query("softwareengproject", paginatedResultGraphQLRequest).toObservable())
                .doOnNext(response -> {
                    i++;
                    Log.e(tag, "count = " + i);
                    if (response.hasErrors()) {
                        subject.onError(new Exception(String.format("Query failed: %s", response.getErrors())));
                    } else if (!response.hasData()) {
                        subject.onError(new Exception("Empty response from AppSync."));
                    } else if (response.getData().hasNextResult()) {
                        subject.onNext(response.getData().getRequestForNextResult());
                    } else {
                        subject.onComplete();
                    }
                }).concatMapIterable(GraphQLResponse::getData).observeOn(AndroidScheduler.mainThread())
                .subscribe(new Observer<PricePaidJson>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("loading....");
                        Log.e(tag, "start searching" + postcode);
                    }

                    @Override
                    public void onNext(@NonNull PricePaidJson pricePaidJson) {
                        Gson gson = new Gson();
                        List<PlacePaidData> placePaidData = new ArrayList<>();
                        for (String data : pricePaidJson.getLocationPaid()) {
                            placePaidData.add(gson.fromJson(data, PlacePaidData.class));
                        }
                        adapter = new HistoryAdapter(HistoryActivity.this, placePaidData);
                        binding.list.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                        binding.list.setAdapter(adapter);
                        Log.e(tag, pricePaidJson.toString());
                        onComplete();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(tag, "read error", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(tag, "onComplete");
                        hideWaitDialog();

                    }
                });
    }

    private void getData() {
        setTest();
        Observable.create((ObservableOnSubscribe<List<PlacePaidData>>) emitter -> {
            Log.e(tag, postcode);
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

                    }
                });
    }
}