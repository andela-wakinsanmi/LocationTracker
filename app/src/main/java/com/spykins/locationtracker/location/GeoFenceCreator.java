package com.spykins.locationtracker.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.LocationFence;

public class GeoFenceCreator {

    private double mLocationLatitude;
    private double mLocationLongitude;
    private double mRadius = 100;

    public GeoFenceCreator(double locationLat, double locationLong) {
        mLocationLatitude = locationLat;
        mLocationLongitude = locationLong;
    }

    public void setRadius(double radius) {
        mRadius = radius;
    }

    public AwarenessFence createEnteringAwareness(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return LocationFence.entering(mLocationLatitude, mLocationLongitude, mRadius);
        }
        return null;
    }

    public AwarenessFence createExitingAwareness(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return LocationFence.exiting(mLocationLatitude, mLocationLongitude, mRadius);
        }

        return null;
    }
}
