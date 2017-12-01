package com.spykins.locationtracker;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.spykins.locationtracker.location.GeoFenceReceiver;
import com.spykins.locationtracker.model.GeoData;

import java.util.Calendar;

public class RegisterLocationActivity extends AppCompatActivity {

    private PendingIntent mPendingIntent;
    private GeoFenceReceiver mGeoFenceReceiver;
    private double mLatitude = 6.5540;
    private double mLongitude = 3.3668;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_location);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


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
    }

    public void registerButtonClicked(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpGeoFencing() {
        Intent intent = new Intent(AppConstants.FENCE_RECEIVER_ACTION);
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        mGeoFenceReceiver = new GeoFenceReceiver();
        registerReceiver(mGeoFenceReceiver, new IntentFilter(AppConstants.FENCE_RECEIVER_ACTION));

        AppManager appManager = Injector.provideAppManager();
        Calendar calendar = Calendar.getInstance();
        GeoData geoData = new GeoData(calendar.getTime(), mLatitude, mLongitude, "Andela Office");
        appManager.init(geoData, mPendingIntent, this, true);
    }
}
