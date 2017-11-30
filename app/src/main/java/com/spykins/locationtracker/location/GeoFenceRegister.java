package com.spykins.locationtracker.location;

import android.app.PendingIntent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;

public class GeoFenceRegister {
    private static final String TAG = "com.spykins.GeofenceReg";
    private GoogleApiClient mGoogleApiClient;
    private PendingIntent mPendingIntent;

    public GeoFenceRegister(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        mPendingIntent = pendingIntent;
    }

    protected void registerFence(final String fenceKey, final AwarenessFence fence,
            String fenceKey2, AwarenessFence fence2) {

        Awareness.FenceApi.updateFences(
                mGoogleApiClient,
                new FenceUpdateRequest.Builder()
                        .addFence(fenceKey, fence, mPendingIntent)
                        //.addFence(fenceKey2, fence2, mPendingIntent)
                        .build())
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.d(TAG, "Fence was successfully registered.");
                        } else {
                            Log.d(TAG, "Fence could not be registered: " + status);
                        }
                    }
                });
    }

    protected void unregisterFence(final String fenceKey) {
        Awareness.FenceApi.updateFences(
                mGoogleApiClient,
                new FenceUpdateRequest.Builder()
                        .removeFence(fenceKey)
                        .build()).setResultCallback(new ResultCallbacks<Status>() {
            @Override
            public void onSuccess(@NonNull Status status) {
                Log.i(TAG, "Fence " + fenceKey + " successfully removed.");
            }

            @Override
            public void onFailure(@NonNull Status status) {
                Log.i(TAG, "Fence " + fenceKey + " could NOT be removed.");
            }
        });
    }
}
