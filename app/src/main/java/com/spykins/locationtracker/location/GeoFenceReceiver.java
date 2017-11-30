package com.spykins.locationtracker.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.android.gms.awareness.fence.FenceState;
import com.spykins.locationtracker.AppConstants;

public class GeoFenceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FenceState fenceState = FenceState.extract(intent);
        String fenceKey = fenceState.getFenceKey();

        if (fenceKey.equals(AppConstants.ENTERED_FENCE)) {
            processOnReceive(context, intent, fenceState, fenceKey, true);


        } else if (fenceKey.equals(AppConstants.EXIT_FENCE)) {

        }

    }

    private void processOnReceive(Context context, Intent intent, FenceState fenceState, String fenceKey,
            boolean hasEntered) {

        if (TextUtils.equals(fenceState.getFenceKey(), AppConstants.ENTERED_FENCE)) {
            switch(fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    //Log.i(TAG, "Headphones are plugged in.");
                    break;
                case FenceState.FALSE:
                    //Log.i(TAG, "Headphones are NOT plugged in.");
                    break;
                case FenceState.UNKNOWN:
                    //Log.i(TAG, "The headphone fence is in an unknown state.");
                    break;
            }
        }
    }
}