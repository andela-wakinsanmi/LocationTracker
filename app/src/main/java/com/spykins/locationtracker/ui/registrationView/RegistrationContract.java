package com.spykins.locationtracker.ui.registrationView;

import android.app.PendingIntent;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;

import com.spykins.locationtracker.Util;
import com.spykins.locationtracker.location.GeoFenceReceiver;
import com.spykins.locationtracker.model.GeoData;

public interface RegistrationContract {

    interface ViewModel {

        void setView(RegistrationContract.View view, Util util);

        MutableLiveData<GeoData> getLiveData();

        boolean shouldProceedWithRegisteration(String addressText, String longitudeText, String latitudeText);

        void registerGoogleApiClient(boolean b);
    }

    interface View {
        void setErrorTextViewForAddressText();

        void setErrorTextViewForLocation();

        void registerGeoFenceReceiver(GeoFenceReceiver geoFenceReceiver);

        void setErrorUnKnown();

        void setErrorInLocation();

        void setLocationValueOnView(Location location);

        String fetchLongitudeValue();

        String fetchLatitudeValue();

        String fetchAddressValue();

        PendingIntent getPendingIntent();

        void finishActivity();
    }
}
