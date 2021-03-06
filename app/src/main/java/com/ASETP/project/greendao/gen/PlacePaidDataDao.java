package com.ASETP.project.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.ASETP.project.model.PlacePaidData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PLACE_PAID_DATA".
*/
public class PlacePaidDataDao extends AbstractDao<PlacePaidData, String> {

    public static final String TABLENAME = "PLACE_PAID_DATA";

    /**
     * Properties of entity PlacePaidData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UniqueIdentifier = new Property(0, String.class, "uniqueIdentifier", true, "UNIQUE_IDENTIFIER");
        public final static Property Price = new Property(1, int.class, "price", false, "POSTCODE");
        public final static Property TransferDate = new Property(2, String.class, "transferDate", false, "PRICE");
        public final static Property Postcode = new Property(3, String.class, "postcode", false, "TRANSFER_DATE");
        public final static Property PropertyType = new Property(4, String.class, "propertyType", false, "PROPERTY_TYPE");
        public final static Property NewOrOld = new Property(5, String.class, "newOrOld", false, "NEW_OR_OLD");
        public final static Property Duration = new Property(6, String.class, "duration", false, "DURATION");
        public final static Property Paon = new Property(7, String.class, "paon", false, "PAON");
        public final static Property Saon = new Property(8, String.class, "saon", false, "SAON");
        public final static Property Strees = new Property(9, String.class, "strees", false, "STREES");
        public final static Property Locality = new Property(10, String.class, "locality", false, "LOCALITY");
        public final static Property Town = new Property(11, String.class, "town", false, "TOWN");
        public final static Property District = new Property(12, String.class, "district", false, "DISTRICT");
        public final static Property Country = new Property(13, String.class, "country", false, "COUNTRY");
        public final static Property CategoryType = new Property(14, String.class, "categoryType", false, "CATEGORY_TYPE");
        public final static Property RecordStatusS = new Property(15, String.class, "recordStatusS", false, "RECORD_STATUS_S");
    }


    public PlacePaidDataDao(DaoConfig config) {
        super(config);
    }
    
    public PlacePaidDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PlacePaidData entity) {
        stmt.clearBindings();
 
        String uniqueIdentifier = entity.getUniqueIdentifier();
        if (uniqueIdentifier != null) {
            stmt.bindString(1, uniqueIdentifier);
        }
        stmt.bindLong(2, entity.getPrice());
 
        String transferDate = entity.getTransferDate();
        if (transferDate != null) {
            stmt.bindString(3, transferDate);
        }
 
        String postcode = entity.getPostcode();
        if (postcode != null) {
            stmt.bindString(4, postcode);
        }
 
        String propertyType = entity.getPropertyType();
        if (propertyType != null) {
            stmt.bindString(5, propertyType);
        }
 
        String newOrOld = entity.getNewOrOld();
        if (newOrOld != null) {
            stmt.bindString(6, newOrOld);
        }
 
        String duration = entity.getDuration();
        if (duration != null) {
            stmt.bindString(7, duration);
        }
 
        String paon = entity.getPaon();
        if (paon != null) {
            stmt.bindString(8, paon);
        }
 
        String saon = entity.getSaon();
        if (saon != null) {
            stmt.bindString(9, saon);
        }
 
        String strees = entity.getStrees();
        if (strees != null) {
            stmt.bindString(10, strees);
        }
 
        String locality = entity.getLocality();
        if (locality != null) {
            stmt.bindString(11, locality);
        }
 
        String town = entity.getTown();
        if (town != null) {
            stmt.bindString(12, town);
        }
 
        String district = entity.getDistrict();
        if (district != null) {
            stmt.bindString(13, district);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(14, country);
        }
 
        String categoryType = entity.getCategoryType();
        if (categoryType != null) {
            stmt.bindString(15, categoryType);
        }
 
        String recordStatusS = entity.getRecordStatusS();
        if (recordStatusS != null) {
            stmt.bindString(16, recordStatusS);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PlacePaidData entity) {
        stmt.clearBindings();
 
        String uniqueIdentifier = entity.getUniqueIdentifier();
        if (uniqueIdentifier != null) {
            stmt.bindString(1, uniqueIdentifier);
        }
        stmt.bindLong(2, entity.getPrice());
 
        String transferDate = entity.getTransferDate();
        if (transferDate != null) {
            stmt.bindString(3, transferDate);
        }
 
        String postcode = entity.getPostcode();
        if (postcode != null) {
            stmt.bindString(4, postcode);
        }
 
        String propertyType = entity.getPropertyType();
        if (propertyType != null) {
            stmt.bindString(5, propertyType);
        }
 
        String newOrOld = entity.getNewOrOld();
        if (newOrOld != null) {
            stmt.bindString(6, newOrOld);
        }
 
        String duration = entity.getDuration();
        if (duration != null) {
            stmt.bindString(7, duration);
        }
 
        String paon = entity.getPaon();
        if (paon != null) {
            stmt.bindString(8, paon);
        }
 
        String saon = entity.getSaon();
        if (saon != null) {
            stmt.bindString(9, saon);
        }
 
        String strees = entity.getStrees();
        if (strees != null) {
            stmt.bindString(10, strees);
        }
 
        String locality = entity.getLocality();
        if (locality != null) {
            stmt.bindString(11, locality);
        }
 
        String town = entity.getTown();
        if (town != null) {
            stmt.bindString(12, town);
        }
 
        String district = entity.getDistrict();
        if (district != null) {
            stmt.bindString(13, district);
        }
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(14, country);
        }
 
        String categoryType = entity.getCategoryType();
        if (categoryType != null) {
            stmt.bindString(15, categoryType);
        }
 
        String recordStatusS = entity.getRecordStatusS();
        if (recordStatusS != null) {
            stmt.bindString(16, recordStatusS);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public PlacePaidData readEntity(Cursor cursor, int offset) {
        PlacePaidData entity = new PlacePaidData( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // uniqueIdentifier
            cursor.getInt(offset + 1), // price
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // transferDate
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // postcode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // propertyType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // newOrOld
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // duration
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // paon
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // saon
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // strees
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // locality
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // town
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // district
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // country
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // categoryType
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15) // recordStatusS
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PlacePaidData entity, int offset) {
        entity.setUniqueIdentifier(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPrice(cursor.getInt(offset + 1));
        entity.setTransferDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPostcode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPropertyType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNewOrOld(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDuration(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPaon(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSaon(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setStrees(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setLocality(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setTown(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setDistrict(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setCountry(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCategoryType(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setRecordStatusS(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
     }
    
    @Override
    protected final String updateKeyAfterInsert(PlacePaidData entity, long rowId) {
        return entity.getUniqueIdentifier();
    }
    
    @Override
    public String getKey(PlacePaidData entity) {
        if(entity != null) {
            return entity.getUniqueIdentifier();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PlacePaidData entity) {
        return entity.getUniqueIdentifier() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
