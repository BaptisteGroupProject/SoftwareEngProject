package com.ASETP.project.dabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ASETP.project.greendao.gen.DaoMaster;
import com.ASETP.project.greendao.gen.DaoSession;
import com.ASETP.project.greendao.gen.LocationPlacesDao;
import com.ASETP.project.greendao.gen.PlacePaidDataDao;
import com.ASETP.project.model.LocationPlaces;
import com.ASETP.project.model.PlacePaidData;

import java.util.List;

/**
 * @author MirageLee
 * @date 2020/11/29
 */
public class DaoManager {

    private static final String TAG = DaoManager.class.getSimpleName();

    private Context context;

    private volatile static DaoManager manager;

    private volatile static DaoManager locationManager;
    private DaoMaster mDaoMaster;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoSession mDaoSession;
    LocationPlacesDao locationPlacesDao;
    PlacePaidDataDao placePaidDataDao;
    public static String LOCATION = "location";

    public static String DATABASE_NAME = "A";

    /**
     * get Instance
     */
    public static DaoManager getInstance(Context context, String databaseName) {
        if (manager == null || !DATABASE_NAME.equals(databaseName)) {
            Log.e(TAG, databaseName + ".db");
            manager = new DaoManager(context, databaseName);
            DATABASE_NAME = databaseName;
        }
        return manager;
    }

    public static DaoManager getLocationInstance(Context context) {
        if (locationManager == null) {
            locationManager = new DaoManager(context, LOCATION);
        }
        return locationManager;
    }

    private DaoManager(Context context, String dataBaseName) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, dataBaseName + ".db", null);
        mDaoMaster = new DaoMaster(getWritableDatabase(dataBaseName));
        mDaoSession = mDaoMaster.newSession();
        locationPlacesDao = mDaoSession.getLocationPlacesDao();
        placePaidDataDao = mDaoSession.getPlacePaidDataDao();
    }

    private SQLiteDatabase getReadableDatabase(String databaseName) {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, databaseName + ".db", null);
        }
        return mHelper.getReadableDatabase();
    }

    private SQLiteDatabase getWritableDatabase(String databaseName) {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, databaseName + ".db", null);
        }
        return mHelper.getWritableDatabase();
    }

    public void insertOrReplaceLocationPlace(List<LocationPlaces> locationPlaces) {
        locationPlacesDao.insertInTx(locationPlaces);
    }

    public void insertOrReplacePricePlace(List<PlacePaidData> locationPlaces) {
        placePaidDataDao.insertInTx(locationPlaces);
    }

    public List<LocationPlaces> searchLocationPlacesByWhere(String where) {
        return locationPlacesDao.queryBuilder()
                .where(LocationPlacesDao.Properties.Postcode.like(where)).build().list();
    }

    public List<LocationPlaces> getAllLocation() {
        return locationPlacesDao.queryBuilder().build().list();
    }

    public List<LocationPlaces> searchLocationPlacesWithPosition(double minLat, double maxLat, double minLong, double maxLong) {
        return locationPlacesDao.queryBuilder()
                .where(LocationPlacesDao.Properties.Latitude.between(minLat, maxLat))
                .where(LocationPlacesDao.Properties.Longitude.between(minLong, maxLong))
        .orderRaw("RANDOM()").limit(1000).build().list();
    }

    public List<PlacePaidData> searchPlacePaidData(String where) {
        return placePaidDataDao.queryBuilder()
                .where(PlacePaidDataDao.Properties.Postcode.eq(where))
                .orderDesc(PlacePaidDataDao.Properties.TransferDate).build().list();
    }

    public void insertOrReplacePlacePaidData(PlacePaidData placePaidData) {
        placePaidDataDao.insert(placePaidData);
    }


}
