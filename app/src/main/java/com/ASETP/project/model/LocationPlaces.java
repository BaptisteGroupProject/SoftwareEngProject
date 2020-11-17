package com.ASETP.project.model;


/**
 * @author MirageLee
 * @date 2020/11/15
 */
public class LocationPlaces {
    private String postcode;
    private float longitude;
    private float latitude;

    public LocationPlaces(String postcode, float longitude, float latitude) {
        this.postcode = postcode;
        this.longitude = longitude;
        this.latitude = latitude;
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
