package com.spykins.locationtracker;

import android.app.PendingIntent;
import android.content.Context;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.spykins.locationtracker.db.AppSharedPreference;
import com.spykins.locationtracker.db.DbManager;
import com.spykins.locationtracker.location.GeoFenceManager;
import com.spykins.locationtracker.model.GeoData;

public class AppManager implements AppManagerCallback {
    private GeoFenceManager mGeoFenceManager;
    private DbManager mDbManager;
    private AppSharedPreference mAppSharedPreference;
    private GeoData mGeoData;

    public AppManager(GeoFenceManager geoFenceManager, DbManager dbManager, AppSharedPreference appSharedPreference) {
        mGeoFenceManager = geoFenceManager;
        mDbManager = dbManager;
        mAppSharedPreference = appSharedPreference;
        mAppSharedPreference.setAppManagerCallBack(this);
    }

    public void init(GeoData geoData, PendingIntent pendingIntent, Context context) {
        mGeoData = geoData;
        mGeoFenceManager.setLatitude(geoData.getLatitude());
        mGeoFenceManager.setLongitude(geoData.getLongitude());
        mGeoFenceManager.getGeoFenceRegister().setPendingIntent(pendingIntent);

        AwarenessFence enteringAwarenss = mGeoFenceManager.getGeoFenceCreator().createEnteringAwareness(context);
        AwarenessFence exitAwareness = mGeoFenceManager.getGeoFenceCreator().createExitingAwareness(context);
        mGeoFenceManager.registerFence(AppConstants.ENTERED_FENCE, enteringAwarenss, AppConstants.EXIT_FENCE, exitAwareness);
    }

    public void writeEnterGeoFenceTimeStamp() {
        mAppSharedPreference.writeEnterGeoFenceTimeStamp();
    }

    public void computeExitFence() {
        mAppSharedPreference.writeTimeStampWhenExitFence();
    }

    @Override
    public void setTimeDuration(long timeEntered, long timeLeft) {
        mGeoData.settimeSpent(timeLeft - timeEntered);
        mDbManager.getGeoDatabase().geoDataDao().insertDataInDb(mGeoData);
    }
}
