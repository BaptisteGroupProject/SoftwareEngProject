package com.ASETP.project.utils;

import android.content.Context;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.ASETP.project.location.AndroidScheduler;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.datastore.generated.model.LocationPricePaidData;
import com.amplifyframework.rx.RxAmplify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author MirageLee
 * @date 2020/11/8
 */
public class FileUtils {
    Context context;

    String tag = this.getClass().getSimpleName();

    private List<PostcodeList> postcodeLists = new ArrayList<>();

    private final static String rules = "[^,]*,";

    private final static String replaceRules = "(\".*?),(.*?\")";

    private int maxCountPerTerm = 1000 * 10;

    public FileUtils(Context context) {
        this.context = context;
    }

    private String replaceString(String line) {
        Pattern p = Pattern.compile(replaceRules);
        Matcher m = p.matcher(line);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group().replace(",", ""));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    int totalCount = 1;
    int missCount = 0;

    public void readLocationPricePaidData() {
        Observable.create((ObservableOnSubscribe<LocationPricePaidData>) emitter -> {
            String privateFilePate = context.getFilesDir().getAbsolutePath() + File.separator + "pp-complete.csv";
            Log.e(tag, privateFilePate);
            File file = new File(privateFilePate);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                while (totalCount - uploadCount > 1000) {
                    Thread.sleep(1000);
                }
                if (totalCount % maxCountPerTerm == 0) {
                    Log.e(tag + " now count", totalCount + "");
                }
                Pattern pattern = Pattern.compile(rules);
                Matcher matcher = pattern.matcher(line);
                String[] lines = new String[16];
                int i = 0;
                while (matcher.find()) {
                    if (i >= 16) {
                        Log.e(tag + " over length", line);
                    }
                    String temp = matcher.group().replace(",", "");
                    while (temp.charAt(temp.length() - 1) != '\"') {
                        temp = matcher.find() ? temp + matcher.group().replace(",", "") : temp;
                    }
                    lines[i] = temp.trim().replace("\"", "");
                    i++;
                }
                if (lines[3] == null || TextUtils.isEmpty(lines[3])) {
                    missCount++;
                    continue;
                }
                float latitude = 0, longitude = 0;
                for (PostcodeList postcodeList : postcodeLists) {
                    if (lines[3].charAt(0) == postcodeList.getFirstChar()) {
                        for (Postcode postcode : postcodeList.getPostcodes()) {
                            if (lines[3].equals(postcode.postcode)) {
                                latitude = postcode.latitude;
                                longitude = postcode.longitude;
                            }
                        }
                    }
                }
                LocationPricePaidData data = new LocationPricePaidData.Builder()
                        .uniqueIdentifier(lines[0]).price(Integer.parseInt(lines[1]))
                        .transferDate(lines[2]).postcode(lines[3])
                        .propertyType(lines[4]).newOrOld(lines[5])
                        .duration(lines[6]).paon(lines[7])
                        .saon(lines[8]).strees(lines[9])
                        .locality(lines[10]).town(lines[11])
                        .district(lines[12]).country(lines[13])
                        .categoryType(lines[14]).recordStatus(lines[15])
                        .latitude(latitude).longitude(longitude).build();
                totalCount++;
                emitter.onNext(data);
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidScheduler.mainThread()).subscribe(new Observer<LocationPricePaidData>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull LocationPricePaidData locationPricePaidData) {
                uploadPriceLocation(locationPricePaidData);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(tag, "read CSV error", e);
            }

            @Override
            public void onComplete() {
                Log.e(tag, "total upload count" + totalCount);
                Log.e(tag, "total missing count" + missCount);
            }
        });
    }

    private int uploadCount = 0;
    private int failedCount = 0;

    /**
     * @param locationPricePaidData
     */
    private void uploadPriceLocation(LocationPricePaidData locationPricePaidData) {
        RxAmplify.API.mutate("softwareengproject", ModelMutation.create(locationPricePaidData))
                .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe(new SingleObserver<GraphQLResponse<LocationPricePaidData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull GraphQLResponse<LocationPricePaidData> locationPricePaidDataGraphQLResponse) {
                        uploadCount++;
                        if (uploadCount % 1000 == 0) {
                            Log.e(tag + " upload count", uploadCount + "");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        failedCount++;
                        Log.e(tag, "update error", e);
                        if (failedCount % 1000 == 0) {
                            Log.e(tag + " failed upload count", failedCount + "");
                        }
                    }
                });

    }

    public void readPostcodeCSV() {
        Observable.create((ObservableOnSubscribe<PostcodeList>) emitter -> {
            InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("ukpostcodes.csv"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            reader.readLine();
            String line;
            char firstChar = 0;
            PostcodeList list = new PostcodeList();
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] postcodes = line.split(",");
                if (postcodes.length < 4) {
                    count++;
                    continue;
                }
                if (firstChar == 0) {
                    firstChar = postcodes[1].charAt(0);
                    list.setFirstChar(firstChar);
                }
                if (firstChar != postcodes[1].charAt(0)) {
                    emitter.onNext(list);
                    list = new PostcodeList();
                    firstChar = postcodes[1].charAt(0);
                    list.setFirstChar(firstChar);
                }
                Postcode postcode = new Postcode();
                postcode.setPostcode(postcodes[1]);
                postcode.setLatitude(Float.parseFloat(postcodes[2]));
                postcode.setLongitude(Float.parseFloat(postcodes[3]));
                list.addPostcodes(postcode);
            }
            emitter.onComplete();
            Log.e(tag, "total missing count = " + count);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidScheduler.mainThread())
                .subscribe(new Observer<PostcodeList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PostcodeList s) {
                        postcodeLists.add(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(tag, "read CSV error", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(tag, postcodeLists.get(postcodeLists.size() - 1).getFirstChar() + "");
                        readLocationPricePaidData();
                    }
                });
    }

    private class PostcodeList {
        private char firstChar;
        private List<Postcode> postcodes = new ArrayList<>();

        public char getFirstChar() {
            return firstChar;
        }

        public void setFirstChar(char firstChar) {
            this.firstChar = firstChar;
        }

        public List<Postcode> getPostcodes() {
            return postcodes;
        }

        public void addPostcodes(Postcode postcode) {
            this.postcodes.add(postcode);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (Postcode postcode : postcodes) {
                builder.append(postcode.toString()).append("\n");
            }
            return "PostcodeList{" +
                    "firstChar='" + firstChar + '\'' +
                    ", postcodes=" + builder +
                    '}';
        }
    }

    public class Postcode {
        private String postcode;
        private float latitude;
        private float longitude;

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return "Postcode{" +
                    "postcode='" + postcode + '\'' +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }

}
