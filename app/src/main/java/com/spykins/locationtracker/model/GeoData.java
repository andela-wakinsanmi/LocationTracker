package com.spykins.locationtracker.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class GeoData {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date mDate;

    private double mLatitude;
    private double mLongitude;
    private String mAddress;
    private double timeSpent;

    @Ignore
    public GeoData(Date date, double latitude, double longitude, String address) {
        this.mDate = date;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        address = address;
    }

    public GeoData(int id, Date date, double latitude, double longitude, String address, double timeSpent) {
        this.mDate = date;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        address = address;
        this.timeSpent = timeSpent;
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return mDate;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public String getAddress() {
        return mAddress;
    }

    public void settimeSpent(long timeStamp) {
        this.timeSpent = timeStamp;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
}
