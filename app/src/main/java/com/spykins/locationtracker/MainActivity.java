package com.spykins.locationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spykins.locationtracker.ui.registrationView.RegisterLocationActivity;

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
