package com.spykins.locationtracker.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.spykins.locationtracker.AppConstants;
import com.spykins.locationtracker.AppManagerCallback;

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
        mAppManagerCallBack.setTimeDuration(timeEntered, timeLeft);
    }

    public void setAppManagerCallBack(AppManagerCallback appManagerCallBack) {
        mAppManagerCallBack = appManagerCallBack;
    }
}
