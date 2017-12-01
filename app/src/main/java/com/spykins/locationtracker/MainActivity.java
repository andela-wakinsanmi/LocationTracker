package com.spykins.locationtracker;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.common.api.GoogleApiClient;
import com.spykins.locationtracker.location.GeoFenceManager;
import com.spykins.locationtracker.location.GeoFenceReceiver;
import com.spykins.locationtracker.ui.GeoRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private PendingIntent mPendingIntent;
    private GeoFenceReceiver mGeoFenceReceiver;
    private RecyclerView mRecyclerView;
    private GeoRecyclerViewAdapter mGeoRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Injector.setApplicationContext(getApplicationContext());
        Injector.provideGoogleApiClient(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                Log.d("wale", "log onConnected");
                setUpGeoFencing();
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d("wale", "log onConnectionSuspended");

            }
        }).connect();

        mGeoRecyclerViewAdapter = new GeoRecyclerViewAdapter();
        mRecyclerView = findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mGeoRecyclerViewAdapter);

    }

    private void setUpGeoFencing() {
        Intent intent = new Intent(AppConstants.FENCE_RECEIVER_ACTION);
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        mGeoFenceReceiver = new GeoFenceReceiver();
        registerReceiver(mGeoFenceReceiver, new IntentFilter(AppConstants.FENCE_RECEIVER_ACTION));

        GeoFenceManager geoFenceManager = Injector.provideGeoManager();
        geoFenceManager.setLatitude(6.553961);
        geoFenceManager.setLongitude(3.366649);
        geoFenceManager.getGeoFenceRegister().setPendingIntent(mPendingIntent);
        AwarenessFence enteringAwarenss = geoFenceManager.getGeoFenceCreator().createEnteringAwareness(this);
        AwarenessFence exitAwareness = geoFenceManager.getGeoFenceCreator().createExitingAwareness(this);
        geoFenceManager.registerFence(AppConstants.ENTERED_FENCE,enteringAwarenss, AppConstants.EXIT_FENCE, exitAwareness);
    }
}
