package com.ASETP.project.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author MirageLee
 * @date 2020/11/15
 */
@Entity
public class LocationPlaces {
    @Id
    private String postcode;
    private float longitude;
    private float latitude;


    @Generated(hash = 2078363913)
    public LocationPlaces(String postcode, float longitude, float latitude) {
        this.postcode = postcode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Generated(hash = 1362597182)
    public LocationPlaces() {
    }

    @Override
    public String toString() {
        return "LocationPlaces{" +
                "postcode='" + postcode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
