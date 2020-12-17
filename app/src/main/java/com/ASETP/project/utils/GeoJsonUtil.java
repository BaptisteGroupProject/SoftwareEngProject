package com.ASETP.project.utils;

import android.content.Context;
import android.util.Log;

import com.ASETP.project.location.AndroidScheduler;
import com.ASETP.project.model.GeoJson;
import com.ASETP.project.model.LocationPlaces;
import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.datastore.generated.model.LocationPlaceByJson;
import com.amplifyframework.rx.RxAmplify;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * @author MirageLee
 * @date 2020/12/16
 */
public class GeoJsonUtil {

    private Context context;

    public static String TYPE = "FeatureCollection";

    public static String GEO_TYPE = "Feature";

    public static String POINT = "Point";

    private String tag = getClass().getSimpleName();

    private int countPerTerm = 10000;

    public GeoJsonUtil(Context context) {
        this.context = context;
    }

    int responseCount = 1;

    public void getData() {
        BehaviorSubject<GraphQLRequest<PaginatedResult<LocationPlaceByJson>>> subject =
                BehaviorSubject.createDefault(ModelQuery.list(LocationPlaceByJson.class));
        subject.concatMap(paginatedResultGraphQLRequest -> RxAmplify.API.query("softwareengproject", paginatedResultGraphQLRequest).toObservable())
                .doOnNext(response -> {
                    if (response.hasErrors()) {
                        subject.onError(new Exception(String.format("Query failed: %s", response.getErrors())));
                    } else if (!response.hasData()) {
                        subject.onError(new Exception("Empty response from AppSync."));
                    } else if (response.getData().hasNextResult()) {
                        subject.onNext(response.getData().getRequestForNextResult());
                    } else {
                        subject.onComplete();
                    }
                }).concatMapIterable(GraphQLResponse::getData).observeOn(Schedulers.io()).subscribe(new Observer<LocationPlaceByJson>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                Log.e(tag, "start");
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull LocationPlaceByJson locationPlaceByJson) {
                Log.e(tag, "response count = " + responseCount++);
                if (responseCount % 500 == 0) {
                    try {
                        writeToFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(tag, "ddd", e);
                    }
                }
                paringLocationPlace(locationPlaceByJson);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.e(tag, "read error");
            }

            @Override
            public void onComplete() {
                try {
                    writeToFile();
                    Log.e(tag, "read complete");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(tag, "write error", e);
                }
            }
        });
    }

    public JSONObject createJsonFileObject(int i)
            throws IOException, JSONException {
        File file = new File(context.getFilesDir().getAbsoluteFile(), "geoJson" + i + ".json");
        Log.e(tag, file.getAbsolutePath());
        String line;
        StringBuilder result = new StringBuilder();
        // Reads from stream
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // Read each line of the GeoJSON file into a string
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        // Converts the result string into a JSONObject
        return new JSONObject(result.toString());
    }

    int i = 1;

    private void writeToFile() throws IOException {
        geoJson.setType(TYPE + i);
        geoJson.setFeatures(x);
        Gson gson = new Gson();
        String s = gson.toJson(geoJson);
        File file = new File(context.getFilesDir().getAbsolutePath(), "geoJson" + i++ + ".json");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(s);
        bufferedWriter.close();
        geoJson = new GeoJson();
        x = new ArrayList<>();
    }

    GeoJson geoJson = new GeoJson();
    List<GeoJson.FeaturesBean> x = new ArrayList<>();

    private void paringLocationPlace(LocationPlaceByJson locationPlaceByJson) {
        List<LocationPlaces> locationPlaces = new ArrayList<>();
        Gson gson = new Gson();
        for (String temp : locationPlaceByJson.getLocationItems()) {
            LocationPlaces locationPlaces1 = gson.fromJson(temp, LocationPlaces.class);
            locationPlaces.add(locationPlaces1);
        }
        List<GeoJson.FeaturesBean> featuresBeans = new ArrayList<>();
        for (int i = locationPlaces.size() - 1; i >= 0; i--) {
            GeoJson.FeaturesBean bean = new GeoJson.FeaturesBean();
            bean.setType(GEO_TYPE);
            GeoJson.FeaturesBean.GeometryBean geometryBean = new GeoJson.FeaturesBean.GeometryBean();
            geometryBean.setType(POINT);
            List<Double> point = new ArrayList<>();
            point.add((double) locationPlaces.get(i).getLongitude());
            point.add((double) locationPlaces.get(i).getLatitude());
            geometryBean.setCoordinates(point);
            GeoJson.FeaturesBean.PropertiesBean propertiesBean = new GeoJson.FeaturesBean.PropertiesBean();
            propertiesBean.setName(locationPlaces.get(i).getPostcode());
            bean.setGeometry(geometryBean);
            bean.setProperties(propertiesBean);
            featuresBeans.add(bean);
            locationPlaces.remove(i);
        }
        x.addAll(featuresBeans);
        Log.e(tag, x.size() + "");
    }


    public void toGeo(List<LocationPlaces> data) {
        Observable.create((ObservableOnSubscribe<List<GeoJson.FeaturesBean>>) emitter -> {
            List<GeoJson.FeaturesBean> featuresBeans = new ArrayList<>();
            Log.e(tag, "0");
            Log.e(tag, data.size() + "");
            int x = 0;
            for (int i = data.size() - 1; i >= 0; i--) {
                Log.e(tag, x++ + "");
                GeoJson.FeaturesBean bean = new GeoJson.FeaturesBean();
                bean.setType(GEO_TYPE);
                GeoJson.FeaturesBean.GeometryBean geometryBean = new GeoJson.FeaturesBean.GeometryBean();
                geometryBean.setType(POINT);
                List<Double> point = new ArrayList<>();
                point.add((double) data.get(i).getLongitude());
                point.add((double) data.get(i).getLatitude());
                geometryBean.setCoordinates(point);
                GeoJson.FeaturesBean.PropertiesBean propertiesBean = new GeoJson.FeaturesBean.PropertiesBean();
                propertiesBean.setName(data.get(i).getPostcode());
                bean.setGeometry(geometryBean);
                bean.setProperties(propertiesBean);
                featuresBeans.add(bean);
                data.remove(i);
            }
            emitter.onNext(featuresBeans);
            emitter.onComplete();
        }).subscribeOn(Schedulers.single()).observeOn(Schedulers.io())
                .subscribe(new Observer<List<GeoJson.FeaturesBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        Log.e(tag, "start read");
                    }

                    @Override
                    public void onNext(@NonNull List<GeoJson.FeaturesBean> featuresBeans) {
                        x.addAll(featuresBeans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(tag, "read error", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(tag, "complete");
                    }
                });

    }

    private void saveToJson(GeoJson geoJson) {

    }
}
