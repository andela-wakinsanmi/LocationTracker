package com.spykins.locationtracker.location;

import com.google.android.gms.awareness.fence.AwarenessFence;

public class GeoFenceManager {

    private GeoFenceRegister mGeoFenceRegister;
    private GeoFenceCreator mGeoFenceCreator;

    public GeoFenceManager(GeoFenceRegister geoFenceRegister, GeoFenceCreator geoFenceCreator) {
        mGeoFenceRegister = geoFenceRegister;
        mGeoFenceCreator = geoFenceCreator;
    }

    public void setLatitude(double latitude) {
        mGeoFenceCreator.setLocationLatitude(latitude);
    }

    public void setLongitude(double longitude) {
        mGeoFenceCreator.setLocationLongitude(longitude);
    }

    public GeoFenceRegister getGeoFenceRegister() {
        return mGeoFenceRegister;
    }

    public GeoFenceCreator getGeoFenceCreator() {
        return mGeoFenceCreator;
    }

    public void registerFence(String firstKey, AwarenessFence firstFence, String secondKey, AwarenessFence secondFence) {
        mGeoFenceRegister.registerFence(firstKey, firstFence, secondKey, secondFence);
    }
}
