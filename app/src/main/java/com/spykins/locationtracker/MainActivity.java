package com.spykins.locationtracker;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spykins.locationtracker.location.GeoFenceReceiver;

public class MainActivity extends AppCompatActivity {

    private PendingIntent mPendingIntent;
    private GeoFenceReceiver mGeoFenceReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Injector.provideGoogleApiClient().connect();

        Intent intent = new Intent(AppConstants.FENCE_RECEIVER_ACTION);
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        mGeoFenceReceiver = new GeoFenceReceiver();
        registerReceiver(mGeoFenceReceiver, new IntentFilter(AppConstants.FENCE_RECEIVER_ACTION));
    }
}
