package com.spykins.locationtracker.db;

public class DbManager {
    private GeoDatabase mGeoDatabase;

    public DbManager(GeoDatabase geoDatabase) {
        mGeoDatabase = geoDatabase;
    }

    public GeoDatabase getGeoDatabase() {
        return mGeoDatabase;
    }
}
