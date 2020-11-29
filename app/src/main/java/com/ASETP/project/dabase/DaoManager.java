package com.ASETP.project.dabase;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ASETP.project.BuildConfig;
import com.ASETP.project.greendao.gen.DaoMaster;
import com.ASETP.project.greendao.gen.DaoSession;
import com.ASETP.project.greendao.gen.LocationPlacesDao;
import com.ASETP.project.greendao.gen.PlacePaidDataDao;
import com.ASETP.project.model.LocationPlaces;
import com.ASETP.project.model.PlacePaidData;
import com.amplifyframework.datastore.generated.model.LocationPlace;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @author MirageLee
 * @date 2020/11/29
 */
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private static final String DB_NAME = "RECORD_DB";

    private Context context;

    private volatile static DaoManager manager;
    private DaoMaster mDaoMaster;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoSession mDaoSession;
    LocationPlacesDao locationPlacesDao;
    PlacePaidDataDao placePaidDataDao;

    /**
     * get Instance
     */
    public static DaoManager getInstance(Context context) {
        if (manager == null) {
            if (manager == null) {
                manager = new DaoManager(context);
            }
        }
        return manager;
    }

    private DaoManager(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, "location.db", null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        locationPlacesDao = mDaoSession.getLocationPlacesDao();
        placePaidDataDao = mDaoSession.getPlacePaidDataDao();
    }

    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "location.db", null);
        }
        return mHelper.getReadableDatabase();
    }

    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "location.db", null);
        }
        return mHelper.getWritableDatabase();
    }

    public void insertOrReplaceLocationPlace(List<LocationPlaces> locationPlaces) {
        locationPlacesDao.insertInTx(locationPlaces);
    }

    public List<LocationPlaces> searchLocationPlacesByWhere(String where) {
        return locationPlacesDao.queryBuilder()
                .where(LocationPlacesDao.Properties.Postcode.like(where)).build().list();
    }

    public List<LocationPlaces> searchLocationPlacesWithPosition(double minLat, double maxLat, double minLong, double maxLong) {
        return locationPlacesDao.queryBuilder()
                .where(LocationPlacesDao.Properties.Latitude.between(minLat, maxLat))
                .where(LocationPlacesDao.Properties.Longitude.between(minLong, maxLong)).build().list();
    }

    public List<PlacePaidData> searchPlacePaidData(String where) {
        return placePaidDataDao.queryBuilder()
                .where(PlacePaidDataDao.Properties.Postcode.eq(where))
                .orderDesc(PlacePaidDataDao.Properties.TransferDate).build().forCurrentThread().list();
    }

    public void insertOrReplacePlacePaidData(PlacePaidData placePaidData) {
        placePaidDataDao.insert(placePaidData);
    }


}
