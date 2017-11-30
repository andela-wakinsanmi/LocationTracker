package com.spykins.locationtracker;

import android.content.Context;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.api.GoogleApiClient;

public class Injector {
    private static Context sContext;

    public static GoogleApiClient provideGoogleApiClient() {
        return new GoogleApiClient.Builder(provideContext())
                .addApi(Awareness.API)
                .build();
        //client.connect();

    }

    private static Context provideContext() {
        if (sContext == null) {
            throw new RuntimeException("You need to set Context.. call "
                    + "setApplicationContext(Context context) in your Application ");
        }
        return sContext ;
    }
}
