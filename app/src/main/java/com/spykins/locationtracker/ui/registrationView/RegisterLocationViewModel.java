package com.spykins.locationtracker.ui.registrationView;

import static android.content.Context.LOCATION_SERVICE;

import android.app.Activity;
import android.app.PendingIntent;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
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

    private final String TAG = RegisterLocationViewModel.class.getSimpleName();
    private WeakReference<RegistrationContract.View> mView;
    private Util mUtil;
    GeoFenceReceiver mGeoFenceReceiver = new GeoFenceReceiver();
    private AppManager mAppManager;
    private boolean isLocationSettingsOn;
    private GoogleApiClient mGoogleApiClient;
    private boolean mShouldSetUpGeoFencing;


    public RegisterLocationViewModel() {
    }

    @Override
    public void setView(RegistrationContract.View view, Util util) {
        mView = new WeakReference<RegistrationContract.View>(view);
        mUtil = util;
    }

    @Override
    public boolean shouldProceedWithRegisteration(String addressText, String longitudeText,
            String latitudeText) {
        boolean isValidDouble = mUtil.isValidDouble(longitudeText) && mUtil.isValidDouble(
                latitudeText);
        RegistrationContract.View view = mView.get();
        if (view != null) {
            if (addressText.isEmpty()) {
                view.setErrorTextViewForAddressText();
                return false;
            }

            if (!isValidDouble) {
                view.setErrorTextViewForLocation();
                return false;
            }
        }

        return true;
    }

    public void registerGoogleApiClient(final boolean shouldSetUpGeoFencing) {
        mShouldSetUpGeoFencing = shouldSetUpGeoFencing;
        mGoogleApiClient = Injector.provideGoogleApiClient(
                new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        setUpGeoFencingInNeccessary();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                });

        checkIfPermissionIsOn();
    }

    private void setUpGeoFencingInNeccessary() {
        Log.d("wale", "log onConnected");
        mAppManager = Injector.provideAppManager();
        if (mShouldSetUpGeoFencing) {
            setUpGeoFencing();
        } else {
            fetchCurrentLocation();
        }
    }

    private void setUpGeoFencing() {
        RegistrationContract.View view = mView.get();

        if (view != null) {
            view.registerGeoFenceReceiver(mGeoFenceReceiver);

            Calendar calendar = Calendar.getInstance();
            String longitude = view.fetchLongitudeValue();
            String latitude = view.fetchLatitudeValue();
            String address = view.fetchAddressValue();

            GeoData geoData;
            if (mUtil.isValidDouble(latitude) && mUtil.isValidDouble(longitude)
                    && !address.isEmpty()) {
                geoData = new GeoData(calendar.getTime(), Double.valueOf(latitude),
                        Double.valueOf(longitude), address);
            } else {
                view.setErrorUnKnown();
                return;
            }

            PendingIntent pendingIntent = view.getPendingIntent();
            Context context = (Context) view;
            mAppManager.init(geoData, pendingIntent, context);
            view.finishActivity();
        }

    }

    private void fetchCurrentLocation() {

        final RegistrationContract.View view = mView.get();

        try {
            if (view != null) {
                Context context = (Context) view;

                mAppManager.fetchCurrentUserLocation(context).observe((LifecycleOwner) context,
                        new Observer<Location>() {
                            @Override
                            public void onChanged(@Nullable Location location) {
                                if (location == null) {
                                    view.setErrorInLocation();
                                } else {
                                    view.setLocationValueOnView(location);
                                }
                            }
                        });
            }

        } catch (NullPointerException e) {
            Log.d(TAG, "The View is null");
        }
    }

    public void showLocationSettingsIfLocationIsOff() {
        RegistrationContract.View view = mView.get();
        if (view != null && !hasEnabledDeviceGps()) {
            view.startSettingsActivity();
            isLocationSettingsOn = false;
        } else {
            isLocationSettingsOn = true;
        }
        checkIfConditionToConnectIsSatisfied();
    }

    private void checkIfConditionToConnectIsSatisfied() {
        if (isLocationSettingsOn && isLocationPermissionOn()) {
            Log.d(TAG, "LocationSetting is on and permission is on");
            setUpApp();
            //Allow Application
        }
    }

    private void setUpApp() {
        if (mGoogleApiClient.isConnected()) {
            setUpGeoFencingInNeccessary();
        } else {
            mGoogleApiClient.connect();
        }
    }

    private boolean isLocationPermissionOn() {
        RegistrationContract.View view = mView.get();
        if (view != null) {
            return view.isFineLocationPermissionGranted();
        }
        return false;
    }


    private void checkIfPermissionIsOn() {

        RegistrationContract.View view = mView.get();

        if (view != null) {
            if (!view.isFineLocationPermissionGranted()) {
                view.requestForPermission();
            } else {
                showLocationSettingsIfLocationIsOff();
            }
        }
    }


    public boolean hasEnabledDeviceGps() {
        RegistrationContract.View view = mView.get();
        if (view != null) {
            LocationManager locationManager = (LocationManager) ((Activity) view).getSystemService(
                    LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return false;
    }
}
