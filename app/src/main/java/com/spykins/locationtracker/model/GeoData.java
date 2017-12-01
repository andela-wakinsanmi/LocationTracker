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
    private long mEnteredTime;
    private long mExitTime;

    @Ignore
    public GeoData(Date date, double latitude, double longitude, String address) {
        this.mDate = date;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        address = address;
    }

    public GeoData(int id, Date date, double latitude, double longitude, String address,
            double timeSpent, long enteredTime, long exitTime) {
        this.id = id;
        mDate = date;
        mLatitude = latitude;
        mLongitude = longitude;
        mAddress = address;
        this.timeSpent = timeSpent;
        mEnteredTime = enteredTime;
        mExitTime = exitTime;
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

    public long getEnteredTime() {
        return mEnteredTime;
    }

    public long getExitTime() {
        return mExitTime;
    }

    public void setTimeSpent(long timeStamp) {
        this.timeSpent = timeStamp;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public void setEnteredTime(long enteredTime) {
        mEnteredTime = enteredTime;
    }

    public void setExitTime(long exitTime) {
        mExitTime = exitTime;
    }
}
