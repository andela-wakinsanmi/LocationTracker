package com.spykins.locationtracker;

import android.app.PendingIntent;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.spykins.locationtracker.db.AppSharedPreference;
import com.spykins.locationtracker.db.DbManager;
import com.spykins.locationtracker.location.GeoFenceManager;
import com.spykins.locationtracker.model.GeoData;

import java.util.List;

public class AppManager implements AppManagerCallback {
    private GeoFenceManager mGeoFenceManager;
    private DbManager mDbManager;
    private AppSharedPreference mAppSharedPreference;
    private MutableLiveData<Location> mLocationMutableLiveData;

    public AppManager(GeoFenceManager geoFenceManager, DbManager dbManager, AppSharedPreference appSharedPreference) {
        mGeoFenceManager = geoFenceManager;
        mDbManager = dbManager;
        mAppSharedPreference = appSharedPreference;
        mLocationMutableLiveData = new MutableLiveData<>();
        mAppSharedPreference.setAppManagerCallBack(this);
    }

    public void init(GeoData geoData, PendingIntent pendingIntent, Context context) {
        mAppSharedPreference.writeTrackingInformation(geoData);
        mGeoFenceManager.setLatitude(geoData.getLatitude());
        mGeoFenceManager.setLongitude(geoData.getLongitude());
        mGeoFenceManager.getGeoFenceRegister().setPendingIntent(pendingIntent);

        AwarenessFence enteringAwarenss = mGeoFenceManager.getGeoFenceCreator().createEnteringAwareness(context);
        AwarenessFence exitAwareness = mGeoFenceManager.getGeoFenceCreator().createExitingAwareness(context);
        mGeoFenceManager.registerFence(AppConstants.ENTERED_FENCE, enteringAwarenss, AppConstants.EXIT_FENCE, exitAwareness);

    }

    public MutableLiveData<Location> fetchCurrentUserLocation(Context context) {
        mGeoFenceManager.getGeoFenceCreator().getLocation(context,
                mGeoFenceManager.getGeoFenceRegister().getGoogleApiClient(), this);
        return mLocationMutableLiveData;
    }

    //Entering geofence
    public void writeEnterGeoFenceTimeStamp() {
        mAppSharedPreference.writeEnterGeoFenceTimeStamp();
    }

    //exit geofence
    public void computeExitFence() {
        mAppSharedPreference.writeTimeStampWhenExitFence();
    }

    @Override
    public void setTimeDuration(GeoData geoData) {
        mDbManager.getGeoDatabase().geoDataDao().insertDataInDb(geoData);
    }

    @Override
    public void setLocation(Location location) {
        mLocationMutableLiveData.setValue(location);
    }

    public List<GeoData> fetchAllGeoDataFromDb() {
        return mDbManager.getGeoDatabase().geoDataDao().fetchAllGeodata();
    }
}
