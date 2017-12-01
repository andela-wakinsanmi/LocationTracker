package com.spykins.locationtracker.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.android.gms.awareness.fence.FenceState;
import com.spykins.locationtracker.AppConstants;
import com.spykins.locationtracker.AppManager;
import com.spykins.locationtracker.Injector;
import com.spykins.locationtracker.db.AppSharedPreference;

public class GeoFenceReceiver extends BroadcastReceiver {
    private AppManager mAppManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        FenceState fenceState = FenceState.extract(intent);
        mAppManager = Injector.provideAppManager();
        processEnterFence(context, intent, fenceState);
    }

    private void processEnterFence(Context context, Intent intent, FenceState fenceState) {
        String fenceKey = fenceState.getFenceKey();

        if (TextUtils.equals(fenceKey, AppConstants.ENTERED_FENCE)) {
            switch(fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    mAppManager.writeEnterGeoFenceTimeStamp();
                    break;
                case FenceState.FALSE:
                    break;
                case FenceState.UNKNOWN:
                    //Log.i(TAG, "The headphone fence is in an unknown state.");
                    break;
            }
        } else if (TextUtils.equals(fenceKey, AppConstants.EXIT_FENCE)) {
            mAppManager.computeExitFence();

        }
    }
}