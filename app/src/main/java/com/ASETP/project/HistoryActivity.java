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
import com.ASETP.project.utils.QueryFactory;
import com.amazonaws.amplify.generated.graphql.QueryByPostcodeQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amplifyframework.api.aws.GsonVariablesSerializer;
import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.QueryType;
import com.amplifyframework.api.graphql.SimpleGraphQLRequest;
import com.amplifyframework.api.graphql.model.ModelPagination;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.datastore.generated.model.PricePaidJson;
import com.amplifyframework.rx.RxAmplify;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.libraries.places.api.model.Place;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.Nonnull;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
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
        QueryFactory.init(this);
        queryByPostcode();
    }

    private void queryByPostcode() {
        Completable.create(emitter -> QueryFactory.awsAppSyncClient().query(QueryByPostcodeQuery.builder().postcode(postcode).build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(callback(emitter))).subscribeOn(Schedulers.io()).observeOn(AndroidScheduler.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(tag, "start read");
                        showWaitDialog("loading...");
                    }

                    @Override
                    public void onComplete() {
                        hideWaitDialog();
                        adapter = new HistoryAdapter(HistoryActivity.this, data);
                        binding.list.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                        binding.list.setAdapter(adapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        Log.e(tag, "query error", e);
                        binding.noData.setVisibility(View.VISIBLE);
                    }
                });
    }

    private GraphQLCall.Callback<QueryByPostcodeQuery.Data> callback(CompletableEmitter completableEmitter) {
        return new GraphQLCall.Callback<QueryByPostcodeQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<QueryByPostcodeQuery.Data> response) {
                if (response.data() == null) {
                    Log.e(tag, "data null");
                    return;
                }
                convertToData(response);
                completableEmitter.onComplete();
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(tag, "fetch error", e);
            }
        };
    }

    private void convertToData(Response<QueryByPostcodeQuery.Data> response) {
        Gson gson = new Gson();
        //........
        QueryByPostcodeQuery.Item item = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(response.data()).queryByPostcode()).items()).get(0);
        for (String model : item.locationPaid()) {
            boolean isRepeat = false;
            PlacePaidData placePaidData = gson.fromJson(model, PlacePaidData.class);
            for (PlacePaidData xData : data) {
                if (placePaidData.getUniqueIdentifier().equals(xData.getUniqueIdentifier())) {
                    isRepeat = true;
                    break;
                }
            }
            if (!isRepeat) {
                Log.e(tag, placePaidData.toString());
                data.add(placePaidData);
            }
        }
        sortByDate();
    }

    private void sortByDate() {
        Collections.sort(data, (o1, o2) -> {
            try {
                return Objects.requireNonNull(new SimpleDateFormat("yyyy-MM-dd HH:ss", Locale.ENGLISH)
                        .parse(o2.getTransferDate()))
                        .compareTo(new SimpleDateFormat("yyyy-MM-dd HH:ss", Locale.ENGLISH).parse(o1.getTransferDate()));
            } catch (ParseException e) {
                return 0;
            }
        });
    }
}