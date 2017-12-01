package com.spykins.locationtracker.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.LocationFence;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.spykins.locationtracker.AppManagerCallback;

public class GeoFenceCreator {

    private double mLocationLatitude = 0;
    private double mLocationLongitude = 0;
    private double mRadius = 100;

    public void setLocationLatitude(double latitude) {
        mLocationLatitude = latitude;
    }

    public void setLocationLongitude(double longitude) {
        mLocationLongitude = longitude;
    }

    public void setRadius(double radius) {
        mRadius = radius;
    }

    public AwarenessFence createEnteringAwareness(Context context) {


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return LocationFence.in(mLocationLatitude, mLocationLongitude, mRadius, 0L);
        } else {
            //request for permission from user
        }
        return null;
    }

    public AwarenessFence createExitingAwareness(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return LocationFence.exiting(mLocationLatitude, mLocationLongitude, mRadius);
        }

        return null;
    }

    public void getLocation(Context context, GoogleApiClient googleApiClient, final AppManagerCallback appManagerCallback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Awareness.SnapshotApi.getLocation(googleApiClient)
                    .setResultCallback(new ResultCallback<LocationResult>() {
                        @Override
                        public void onResult(@NonNull LocationResult locationResult) {
                            appManagerCallback.setLocation(locationResult.getLocation());

                        }
                    });
        }


    }
}
