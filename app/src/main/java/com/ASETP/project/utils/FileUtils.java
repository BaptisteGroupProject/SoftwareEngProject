package com.ASETP.project.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ASETP.project.dabase.DaoManager;
import com.ASETP.project.location.AndroidScheduler;
import com.ASETP.project.model.LocationPlaces;
import com.ASETP.project.model.PlacePaidData;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.datastore.generated.model.LocationPlace;
import com.amplifyframework.datastore.generated.model.LocationPricePaidData;
import com.amplifyframework.datastore.generated.model.PricePaidJson;
import com.amplifyframework.rx.RxAmplify;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
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

    private String tag = this.getClass().getSimpleName();

    private List<PostcodeList> postcodeLists = new ArrayList<>();

    private final static String rules = "[^,]*,";

    /**
     * print log 10,000 count per term
     */
    private final int maxCountPerTerm = 1000;

    private List<LocationPlaces> locationPlaces = new ArrayList<>();

    private String firstPostcode = "";

    public FileUtils(Context context) {
        this.context = context;
    }


    int totalCount = 1;
    int missCount = 0;

    private BufferedReader getReader() throws FileNotFoundException {
        String privateFilePate = context.getFilesDir().getAbsolutePath() + File.separator + "pp-complete.csv";
        Log.e(tag, privateFilePate);
        File file = new File(privateFilePate);
        Log.e(tag, file.length() + "");
        return new BufferedReader(new FileReader(file));
    }


    /**
     * convert csv line to string lines
     *
     * @param line csv line
     * @return String lines
     * @throws InterruptedException error
     */
    private String[] parsingData(String line) throws InterruptedException {
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
        return lines;
    }

    /**
     * compare the postcode in pricePaidData csv to Postcode csv, find the GPS location
     *
     * @param postcode postcode
     */
    private void addLocationPlace(String postcode) {
        for (LocationPlaces locationPlace : locationPlaces) {
            if (postcode.equals(locationPlace.getPostcode())) {
                return;
            }
        }
        for (PostcodeList postcodeList : postcodeLists) {
            if (postcode.charAt(0) == postcodeList.getFirstChar()) {
                for (Postcode postcodex : postcodeList.getPostcodes()) {
                    if (postcode.equals(postcodex.postcode)) {
                        LocationPlaces locationPlace = new LocationPlaces(postcode, postcodex.longitude, postcodex.latitude);
                        locationPlaces.add(locationPlace);
                    }
                }
            }
        }
    }

    /**
     * get the first part of postcode e.g. BN2 1B1  returns BN2
     *
     * @param postcode postcode
     * @return first part of postcode
     */
    private String getFirstCode(String postcode) {
        String[] first = postcode.split(" ");
        return first[0];
    }

    int uploadCount = 0;
    int total = 0;

    /**
     * upload the place location
     * user uploadCount to check how many data we upload at the same time.
     * if it is over 5, let the file reading thread sleeping loop 0.2s
     *
     * @param firstPostcode first part of postcode
     */
    private void upload(String firstPostcode) {
        List<String> data = new ArrayList<>();
        Gson gson = new Gson();
        /*
         * convert the model to json string,add to the upload model,
         * and remove the model in the locationPlaces list in case the out of memory error
         */
        for (int i = locationPlaces.size() - 1; i >= 0; i--) {
            data.add(gson.toJson(locationPlaces.get(i)));
            locationPlaces.remove(i);
        }
        locationPlaces = new ArrayList<>();
        LocationPlace locationByJson = LocationPlace.builder().locationItems(data).firstPostcode(firstPostcode).build();
        RxAmplify.API.mutate("softwareengproject", ModelMutation.create(locationByJson)).subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread()).subscribe(new SingleObserver<GraphQLResponse<LocationPlace>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                uploadCount++;
            }

            @Override
            public void onSuccess(@NonNull GraphQLResponse<LocationPlace> locationByJsonGraphQLResponse) {
                total += data.size();
                Log.e(tag + "success upload = ", firstPostcode + "size of data = " + data.size() + " total = " + total);
                uploadCount--;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(tag, "upload place error", e);
            }
        });
    }

    /**
     * convert the string list to model
     *
     * @param lines csv data
     * @return PlacePaidData
     */
    private PlacePaidData parsingStringToModel(String[] lines) {
        PlacePaidData placePaidData = new PlacePaidData();
        placePaidData.setUniqueIdentifier(lines[0]);
        placePaidData.setPrice(Integer.parseInt(lines[1]));
        placePaidData.setTransferDate(lines[2]);
        placePaidData.setPostcode(lines[3]);
        placePaidData.setPropertyType(lines[4]);
        placePaidData.setNewOrOld(lines[5]);
        placePaidData.setDuration(lines[6]);
        placePaidData.setPaon(lines[7]);
        placePaidData.setSaon(lines[8]);
        placePaidData.setStrees(lines[9]);
        placePaidData.setLocality(lines[10]);
        placePaidData.setTown(lines[11]);
        placePaidData.setDistrict(lines[12]);
        placePaidData.setCountry(lines[13]);
        placePaidData.setCategoryType(lines[14]);
        placePaidData.setRecordStatusS(lines[15]);
        return placePaidData;
    }

    /**
     * Convert the PlacePaidData model to json string
     *
     * @param placePaidData placePaidData-> the paid history of one place ordered by postcode
     * @return Json string list
     */
    private List<String> parsingPlacePaidDataToJson(List<PlacePaidData> placePaidData) {
        List<String> data = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = placePaidData.size() - 1; i >= 0; i--) {
            data.add(gson.toJson(placePaidData.get(i)));
            placePaidData.remove(i);
        }
        return data;
    }

    /**
     * Read whole file and parsing to string list
     * put it in to model and upload
     * if uploadCount is over 20, make the this thread sleep to prevent the out of memory
     * this would make the upload speed decrease about 20%
     */
    public void readLocationPricePaidToJson() {
        Observable.create((ObservableOnSubscribe<PricePaidJson>) emitter -> {
            List<PlacePaidData> data = new ArrayList<>();
            BufferedReader reader = getReader();
            String postcode = null;
            String line;
            while ((line = reader.readLine()) != null) {
                while (uploadCount >= 20) {
                    Log.e(tag, "sleep 0.2s");
                    Thread.sleep(200);
                }
                String[] lines = parsingData(line);
                if (lines[3] == null || TextUtils.isEmpty(lines[3])) {
                    missCount++;
                    continue;
                }
                if (postcode == null) {
                    postcode = lines[3];
                } else if (!postcode.equals(lines[3])) {
                    PricePaidJson uploadData = PricePaidJson.builder()
                            .locationPaid(parsingPlacePaidDataToJson(data)).postcode(postcode).build();
                    emitter.onNext(uploadData);
                    postcode = lines[3];
                    data = new ArrayList<>();
                }
                data.add(parsingStringToModel(lines));
                totalCount++;
                if (totalCount % maxCountPerTerm == 0) {
                    Log.e(tag, "total count = " + totalCount);
                }
            }
            PricePaidJson uploadData = PricePaidJson.builder()
                    .locationPaid(parsingPlacePaidDataToJson(data)).postcode(postcode).build();
            emitter.onNext(uploadData);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidScheduler.mainThread()).subscribe(new Observer<PricePaidJson>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull PricePaidJson placePaidData) {
                uploadPricePaidDataByJson(placePaidData);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(tag, "parsing failed", e);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    int dataUploadCount = 0;

    /**
     * About the thread, Schedulers.single() and Schedulers.io() is different.
     * single have only 1 thread and the upload will make one by one in line.
     * io thread is a thread group that contains about 30?(or maybe 40) thread.
     * So, using the io thread would make the upload more quick, the problem is when
     * you finish the upload the thread in io would not release immediately, it would release
     * after 1 min (Maybe it's the problem of Single Observable, it doesn't contain the onComplete method so it can't release immediately).
     * so, it would easily lead to out of memory. In that case, using the single thread
     * may be the only solution.
     * Upload the model to back-end
     *
     * @param placePaidData the paid history of one place ordered by postcode
     */
    private void uploadPricePaidDataByJson(PricePaidJson placePaidData) {
        RxAmplify.API.mutate("softwareengproject", ModelMutation.create(placePaidData))
                .subscribeOn(Schedulers.single()).observeOn(AndroidScheduler.mainThread())
                .subscribe(new SingleObserver<GraphQLResponse<PricePaidJson>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        uploadCount++;
                    }

                    @Override
                    public void onSuccess(@NonNull GraphQLResponse<PricePaidJson> locationPricePaidDataByJsonGraphQLResponse) {
                        uploadCount--;
                        dataUploadCount += placePaidData.getLocationPaid().size();
                        Log.e(tag, "upload " + placePaidData.getPostcode() + " success, size = " + placePaidData.getLocationPaid().size() + " dataUploadCount = " + dataUploadCount);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(tag, "upload " + placePaidData.getPostcode() + " failed", e);
                    }
                });
    }

    private void savePlace() {
        DaoManager.getLocationInstance(context).insertOrReplaceLocationPlace(locationPlaces);
        locationPlaces = new ArrayList<>();
    }

    private void savePaidData(String postcode, PlacePaidData data) {
        DaoManager.getInstance(context, String.valueOf(postcode.charAt(0))).insertOrReplacePlacePaidData(data);
    }

    int wholeDataLength = 25530306;
    int readCount = 0;

    public void readWhole() {
        Completable.create(emitter -> {
            BufferedReader reader = getReader();
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lines = parsingData(line);
                if (lines[3] == null || TextUtils.isEmpty(lines[3])) {
                    missCount++;
                    continue;
                }
                if (TextUtils.isEmpty(firstPostcode)) {
                    firstPostcode = getFirstCode(lines[3]);
                } else if (!firstPostcode.equals(getFirstCode(lines[3]))) {
                    savePlace();
                    firstPostcode = getFirstCode(lines[3]);
                }
                addLocationPlace(lines[3]);
                savePlace();
//                savePaidData(lines[3], parsingStringToModel(lines));
                readCount++;
                onReadingListener.onReading((int) (((double) ((double) readCount / (double) wholeDataLength)) * 100));
                if (readCount % maxCountPerTerm == 0) {
                    Log.e(tag, "total count = " + wholeDataLength + " readCount = " + readCount + "reading postcode" + lines[3]);
                }
            }
            savePlace();
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidScheduler.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                onReadingListener.onSuccess();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    /**
     * read the whole data and get the postcode and GPS location
     * when uploadCount is over 5 let the thread sleep 1s
     * in this method, the subscribe method is useless. I put the upload code in the upper body
     * (No reasons to do that, just don't want waste time to review the code and fix the previous problem)
     */
    public void readLocationPricePaidData() {
        Observable.create((ObservableOnSubscribe<LocationPricePaidData>) emitter -> {
            BufferedReader reader = getReader();
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                while (uploadCount > 5) {
                    Thread.sleep(1000);
                }
                String[] lines = parsingData(line);
                if (lines[3] == null || TextUtils.isEmpty(lines[3])) {
                    missCount++;
                    continue;
                }
                if (TextUtils.isEmpty(firstPostcode)) {
                    firstPostcode = getFirstCode(lines[3]);
                    Log.e(tag, "first code :" + firstPostcode);
                } else if (!firstPostcode.equals(getFirstCode(lines[3]))) {
                    Log.e(tag, "upload:" + firstPostcode);
                    upload(firstPostcode);
                    firstPostcode = getFirstCode(lines[3]);
                }
                addLocationPlace(lines[3]);
                totalCount++;
                if (totalCount % maxCountPerTerm == 0) {
                    Log.e(tag, "total count = " + totalCount);
                }
//                emitter.onNext(data);
            }
            upload(firstPostcode);
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

    private int failedCount = 0;


    SingleObserver<GraphQLResponse<LocationPricePaidData>> singleObserver = new SingleObserver<GraphQLResponse<LocationPricePaidData>>() {
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
    };

    /**
     * useless code
     *
     * @param locationPricePaidData locationPricePaidData
     */
    private void uploadPriceLocation(LocationPricePaidData locationPricePaidData) {
        RxAmplify.API.mutate("softwareengproject", ModelMutation.create(locationPricePaidData))
                .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe(singleObserver);
    }

    /**
     * read the whole UK postcode and put in the memory
     * the post code is ordered by the first character of postcode
     * E.G. BN1 2DB then it will be firstChar = B
     * and put the postcode and GPS location into the list body
     * the reason to do this is to save time to search
     * (because the 2.6M postcode data will be divided by 26)
     * then it would not need to loop the whole data every time when compare the postcode in the pricePaidData
     */
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
            emitter.onNext(list);
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
//                        readLocationPricePaidData();
                        readWhole();
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

    public void setOnReadingListener(OnReadingListener onReadingListener) {
        this.onReadingListener = onReadingListener;
    }

    private OnReadingListener onReadingListener;

    public interface OnReadingListener {
        /**
         * show the reading progress
         *
         * @param i progress
         */
        void onReading(int i);

        /**
         * when success
         */
        void onSuccess();
    }

}
