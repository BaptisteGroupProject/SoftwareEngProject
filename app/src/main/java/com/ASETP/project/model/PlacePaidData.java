package com.ASETP.project.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * @author MirageLee
 * @date 2020/11/16
 */
@Entity(nameInDb = "PLACE_PAID_DATA", createInDb = false)
public class PlacePaidData {
    @Property(nameInDb = "UNIQUE_IDENTIFIER")
    @Id
    private String uniqueIdentifier;
    @Property(nameInDb = "POSTCODE")
    private int price;
    @Property(nameInDb = "PRICE")
    private String transferDate;
    @Property(nameInDb = "TRANSFER_DATE")
    private String postcode;
    @Property(nameInDb = "PROPERTY_TYPE")
    private String propertyType;
    @Property(nameInDb = "NEW_OR_OLD")
    private String newOrOld;
    @Property(nameInDb = "DURATION")
    private String duration;
    @Property(nameInDb = "PAON")
    private String paon;
    @Property(nameInDb = "SAON")
    private String saon;
    @Property(nameInDb = "STREES")
    private String strees;
    @Property(nameInDb = "LOCALITY")
    private String locality;
    @Property(nameInDb = "TOWN")
    private String town;
    @Property(nameInDb = "DISTRICT")
    private String district;
    @Property(nameInDb = "COUNTRY")
    private String country;
    @Property(nameInDb = "CATEGORY_TYPE")
    private String categoryType;
    @Property(nameInDb = "RECORD_STATUS_S")
    private String recordStatusS;

    @Generated(hash = 1446764705)
    public PlacePaidData(String uniqueIdentifier, int price, String transferDate,
                         String postcode, String propertyType, String newOrOld, String duration,
                         String paon, String saon, String strees, String locality, String town,
                         String district, String country, String categoryType, String recordStatusS) {
        this.uniqueIdentifier = uniqueIdentifier;
        this.price = price;
        this.transferDate = transferDate;
        this.postcode = postcode;
        this.propertyType = propertyType;
        this.newOrOld = newOrOld;
        this.duration = duration;
        this.paon = paon;
        this.saon = saon;
        this.strees = strees;
        this.locality = locality;
        this.town = town;
        this.district = district;
        this.country = country;
        this.categoryType = categoryType;
        this.recordStatusS = recordStatusS;
    }

    @Generated(hash = 258392492)
    public PlacePaidData() {
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getNewOrOld() {
        return newOrOld;
    }

    public void setNewOrOld(String newOrOld) {
        this.newOrOld = newOrOld;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPaon() {
        return paon;
    }

    public void setPaon(String paon) {
        this.paon = paon;
    }

    public String getSaon() {
        return saon;
    }

    public void setSaon(String saon) {
        this.saon = saon;
    }

    public String getStrees() {
        return strees;
    }

    public void setStrees(String strees) {
        this.strees = strees;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getRecordStatusS() {
        return recordStatusS;
    }

    public void setRecordStatusS(String recordStatusS) {
        this.recordStatusS = recordStatusS;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "PlacePaidData{" +
                "uniqueIdentifier='" + uniqueIdentifier + '\'' +
                ", price=" + price +
                ", transferDate='" + transferDate + '\'' +
                ", postcode='" + postcode + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", newOrOld='" + newOrOld + '\'' +
                ", duration='" + duration + '\'' +
                ", paon='" + paon + '\'' +
                ", saon='" + saon + '\'' +
                ", strees='" + strees + '\'' +
                ", locality='" + locality + '\'' +
                ", town='" + town + '\'' +
                ", district='" + district + '\'' +
                ", country='" + country + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", recordStatusS='" + recordStatusS + '\'' +
                '}';
    }
}
