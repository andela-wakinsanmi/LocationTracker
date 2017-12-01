package com.spykins.locationtracker.ui.registrationView;

import android.app.PendingIntent;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.spykins.locationtracker.AppManager;
import com.spykins.locationtracker.Injector;
import com.spykins.locationtracker.Util;
import com.spykins.locationtracker.location.GeoFenceReceiver;
import com.spykins.locationtracker.model.GeoData;

import java.lang.ref.WeakReference;
import java.util.Calendar;

public class RegisterLocationViewModel extends android.arch.lifecycle.ViewModel implements
        RegistrationContract.ViewModel {

    private  WeakReference<RegistrationContract.View> mView;
    private Util mUtil;
    GeoFenceReceiver mGeoFenceReceiver = new GeoFenceReceiver();
    private AppManager mAppManager;


    public RegisterLocationViewModel() {
    }

    @Override
    public void setView(RegistrationContract.View view, Util util) {
        mView = new WeakReference<RegistrationContract.View>(view);
        mUtil = util;
    }

    @Override
    public MutableLiveData<GeoData> getLiveData() {
        return null;
    }

    @Override
    public boolean shouldProceedWithRegisteration(String addressText, String longitudeText, String latitudeText) {
        boolean isValidDouble = mUtil.isValidDouble(longitudeText) && mUtil.isValidDouble(latitudeText);

        if (addressText.isEmpty()) {
            if (mView.get() != null) mView.get().setErrorTextViewForAddressText();
            return false;
        }

        if (!isValidDouble) {
            if (mView.get() != null) mView.get().setErrorTextViewForLocation();
            return false;
        }

        return true;
    }

    @Override
    public void registerGoogleApiClient(final boolean shouldSetUpGeoFencing) {
        GoogleApiClient googleApiClient = Injector.provideGoogleApiClient(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                setUpGeoFencingInNeccessary(shouldSetUpGeoFencing);
            }

            @Override
            public void onConnectionSuspended(int i) {}
        });

        if (googleApiClient.isConnected()) {
            setUpGeoFencingInNeccessary(shouldSetUpGeoFencing);
        } else {
            googleApiClient.connect();
        }
    }

    private void setUpGeoFencingInNeccessary(boolean shouldSetUpGeoFencing) {
        Log.d("wale", "log onConnected");
        mAppManager = Injector.provideAppManager();
        if (shouldSetUpGeoFencing) {
            setUpGeoFencing();
        } else {
            fetchCurrentLocation();
        }
    }

    private void setUpGeoFencing() {

        if (mView.get() != null) mView.get().registerGeoFenceReceiver(mGeoFenceReceiver);

        Calendar calendar = Calendar.getInstance();
        String longitude = mView.get() != null ? mView.get().fetchLongitudeValue() : "";
        String latitude = mView.get() != null ? mView.get().fetchLatitudeValue() : "";
        String address = mView.get() != null ? mView.get().fetchAddressValue() : "";

        GeoData geoData;
        if (mUtil.isValidDouble(latitude) && mUtil.isValidDouble(longitude) && !address.isEmpty() ) {
            geoData = new GeoData(calendar.getTime(), Double.valueOf(latitude),
                    Double.valueOf(longitude), address);
        } else {
            if (mView.get() != null) mView.get().setErrorUnKnown();
            return;
        }

        PendingIntent pendingIntent = mView.get() != null ? mView.get().getPendingIntent() : null;
        Context context = mView.get() != null ? (Context) mView.get() : null;
        mAppManager.init(geoData, pendingIntent,context );
        if (mView.get() != null) mView.get().finishActivity();
    }

    private void fetchCurrentLocation() {
        Context context =  mView.get() != null ? (Context)mView.get() : null;

        mAppManager.fetchCurrentUserLocation(context).observe((LifecycleOwner) context, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                if (location == null) {
                    if (mView.get() != null) mView.get().setErrorInLocation();
                } else {
                    if (mView.get() != null) mView.get().setLocationValueOnView(location);
                }
            }
        });
    }


}
