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
import android.view.View;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.common.api.GoogleApiClient;
import com.spykins.locationtracker.location.GeoFenceManager;
import com.spykins.locationtracker.location.GeoFenceReceiver;
import com.spykins.locationtracker.model.GeoData;
import com.spykins.locationtracker.ui.GeoRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Injector.setApplicationContext(getApplicationContext());
    }

    public void goToRegistrationScreen(View view) {
        Intent intent = new Intent(this, RegisterLocationActivity.class);
        startActivity(intent);
    }

    public void goToListView(View view) {
        Intent intent = new Intent(this, ViewGeoListActivity.class);
        startActivity(intent);
    }
}
