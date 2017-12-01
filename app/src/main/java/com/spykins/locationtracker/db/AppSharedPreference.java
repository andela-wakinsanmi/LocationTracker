package com.spykins.locationtracker.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.spykins.locationtracker.AppConstants;
import com.spykins.locationtracker.AppManagerCallback;
import com.spykins.locationtracker.model.GeoData;

import java.util.Calendar;

/**
 * This class writes data into the SharedPreference
 * It is Abstracted out to be used in {@link com.spykins.locationtracker.AppManager}
 *  The  app sets the data from the Receiver {@link com.spykins.locationtracker.location.GeoFenceReceiver}
 */
public class AppSharedPreference {

    private Calendar mCalendar;
    private Context mContext;
    private AppManagerCallback mAppManagerCallBack;

    public AppSharedPreference(Context context) {
        mCalendar = Calendar.getInstance();
        mContext = context;
    }

    public void writeEnterGeoFenceTimeStamp() {

        long time = mCalendar.getTimeInMillis();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(AppConstants.WRITE_ENTERED_IN_SHARE_PREFERENCE, time);
        editor.apply();
    }

    public void writeTimeStampWhenExitFence() {
        long timeLeft = mCalendar.getTimeInMillis();
        SharedPreferences sharedPref = mContext.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        long timeEntered = sharedPref.getLong(AppConstants.WRITE_ENTERED_IN_SHARE_PREFERENCE, 0);
        if (timeEntered == 0) return;

        GeoData geoData = computeGeoDataFromPreference(timeEntered, timeLeft);
        mAppManagerCallBack.setTimeDuration(geoData);
    }

    private GeoData computeGeoDataFromPreference(long timeEntered, long timeLeft) {

        SharedPreferences sharedPref = mContext.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        //(Date date, double latitude, double longitude, String address
        double latitude = getDouble(sharedPref, AppConstants.WRITE_LATITUDE, 0);
        double longitude = getDouble(sharedPref, AppConstants.WRITE_LONGITUDE, 0);
        String address = sharedPref.getString(AppConstants.WRITE_ADDRESS, "");
        GeoData geoData = new GeoData(mCalendar.getTime(),  latitude, longitude, address);
        geoData.setTimeSpent(timeLeft - timeEntered);
        geoData.setEnteredTime(timeEntered);
        geoData.setExitTime(timeLeft);
        return geoData;
    }

    public void setAppManagerCallBack(AppManagerCallback appManagerCallBack) {
        mAppManagerCallBack = appManagerCallBack;
    }

    public void writeTrackingInformation(GeoData geoData) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.WRITE_ADDRESS, geoData.getAddress());
        editor.putLong(AppConstants.WRITE_LATITUDE,  Double.doubleToRawLongBits(geoData.getLatitude()));
        editor.putLong(AppConstants.WRITE_LONGITUDE,  Double.doubleToRawLongBits(geoData.getLongitude()));
        editor.apply();
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }
}
