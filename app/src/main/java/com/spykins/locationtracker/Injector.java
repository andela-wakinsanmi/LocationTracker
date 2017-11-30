package com.spykins.locationtracker;

import android.content.Context;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.api.GoogleApiClient;
import com.spykins.locationtracker.location.GeoFenceCreator;
import com.spykins.locationtracker.location.GeoFenceManager;
import com.spykins.locationtracker.location.GeoFenceRegister;

public class Injector {
    private static Context sContext;

    public static GoogleApiClient provideGoogleApiClient() {
        return new GoogleApiClient.Builder(provideContext())
                .addApi(Awareness.API)
                .build();
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
        return new GeoFenceRegister(provideGoogleApiClient());
    }
}
