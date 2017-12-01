package com.spykins.locationtracker;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.api.GoogleApiClient;
import com.spykins.locationtracker.db.AppSharedPreference;
import com.spykins.locationtracker.db.DbManager;
import com.spykins.locationtracker.db.GeoDatabase;
import com.spykins.locationtracker.location.GeoFenceCreator;
import com.spykins.locationtracker.location.GeoFenceManager;
import com.spykins.locationtracker.location.GeoFenceRegister;

public class Injector {
    private static Context sContext;
    private static GoogleApiClient sGoogleApiClient;
    private static AppManager sAppManager;
    private static GeoDatabase sGeoDatabase;
    private static DbManager sDbManager;
    private static final String DB_NAME = "com.spykins.geoDatabase";
    private static final Object LOCK = new Object();


    public static void setApplicationContext(Context context) {
        sContext = context;
    }

    public static GoogleApiClient provideGoogleApiClient(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        if (sGoogleApiClient == null) {
            sGoogleApiClient = new GoogleApiClient.Builder(provideContext())
                    .addApi(Awareness.API)
                    .addConnectionCallbacks(connectionCallbacks)
                    .build();
        }
        return sGoogleApiClient;
    }

    private static Context provideContext() {
        if (sContext == null) {
            throw new RuntimeException("You need to set Context.. call "
                    + "setApplicationContext(Context context) in your Application ");
        }
        return sContext ;
    }

    public static Context provideApplicationContext() {
        return provideContext();
    }

    public static GeoFenceManager provideGeoManager() {
        return new GeoFenceManager(provideGeoRegister(), provideGeoFenceCreator());
    }

    public static GeoFenceCreator provideGeoFenceCreator() {
        return new GeoFenceCreator();
    }

    private static GeoFenceRegister provideGeoRegister() {
        if (sGoogleApiClient == null) {
            throw new RuntimeException("You need to call provideGoogleApiClient before this");
        }
        return new GeoFenceRegister(sGoogleApiClient);
    }

    public static AppManager provideAppManager() {
        if (sAppManager == null) {
            sAppManager = new AppManager(provideGeoManager(), provideDbManager(), provideAppSharedPreference());
        }
        return sAppManager;
    }

    private static AppSharedPreference provideAppSharedPreference() {
        return new AppSharedPreference(provideApplicationContext());
    }

    private static DbManager provideDbManager() {
        if (sDbManager == null) {
            sDbManager = new DbManager(provideGeoDatabase());
        }
        return sDbManager;
    }

    public static GeoDatabase provideGeoDatabase() {
        if (sGeoDatabase == null) {
            synchronized (LOCK) {
                sGeoDatabase = Room.databaseBuilder(provideApplicationContext(),
                        GeoDatabase.class, DB_NAME).allowMainThreadQueries().build();
            }
        }
        return sGeoDatabase;
    }
}
