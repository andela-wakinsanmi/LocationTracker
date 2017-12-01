package com.spykins.locationtracker.ui.registrationView;

import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spykins.locationtracker.AppConstants;
import com.spykins.locationtracker.AppManager;
import com.spykins.locationtracker.Injector;
import com.spykins.locationtracker.R;
import com.spykins.locationtracker.Util;
import com.spykins.locationtracker.location.GeoFenceReceiver;

public class RegisterLocationActivity extends AppCompatActivity implements
        RegistrationContract.View {

    private EditText mAddressText;
    private EditText mLongitudeText;
    private EditText mLatitudeText;
    private TextView mErrorTextView;
    private AppManager appManager;
    private RegistrationContract.ViewModel mViewModel;
    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_location);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Injector.setApplicationContext(getApplicationContext());

        mAddressText = findViewById(R.id.addressEditText);
        mLatitudeText = findViewById(R.id.latitudeEditText);
        mLongitudeText = findViewById(R.id.longitudeEditText);
        mErrorTextView = findViewById(R.id.textViewLocationError);

        mViewModel = (RegistrationContract.ViewModel)ViewModelProviders.of(this).get(RegisterLocationViewModel.class);
        mViewModel.setView(this, new Util());

    }

    public void registerButtonClicked(View view) {

       String addressText = mAddressText.getText().toString().trim();
       String longitudeText = mLongitudeText.getText().toString().trim();
        String latitudeText = mLatitudeText.getText().toString().trim();

       if (mViewModel.shouldProceedWithRegisteration(addressText, longitudeText, latitudeText)) {
           mViewModel.registerGoogleApiClient(true);
       }
    }

    @Override
    public void setErrorTextViewForAddressText() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(getResources().getString(R.string.address_is_empty));
    }

    @Override
    public void setErrorTextViewForLocation() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(getResources().getString(R.string.location_is_empty));
    }

    @Override
    public void registerGeoFenceReceiver(GeoFenceReceiver geoFenceReceiver) {
        Intent intent = new Intent(AppConstants.FENCE_RECEIVER_ACTION);
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        registerReceiver(geoFenceReceiver, new IntentFilter(AppConstants.FENCE_RECEIVER_ACTION));
    }

    @Override
    public void setErrorUnKnown() {
        mErrorTextView.setText(R.string.error_unknown);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setErrorInLocation() {
        mErrorTextView.setText(R.string.error_location);
    }

    @Override
    public void setLocationValueOnView(Location location) {
        mLatitudeText.setText(String.valueOf(location.getLatitude()));
        mLongitudeText.setText(String.valueOf(location.getLongitude()));
        mErrorTextView.setVisibility(View.GONE);
    }

    @Override
    public String fetchLongitudeValue() {
        return mLongitudeText.getText().toString().trim();
    }

    @Override
    public String fetchLatitudeValue() {
        return mLatitudeText.getText().toString().trim();
    }

    @Override
    public String fetchAddressValue() {
        return mAddressText.getText().toString().trim();
    }

    @Override
    public PendingIntent getPendingIntent() {
        return mPendingIntent;
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void errorTexViewClicked(View view) {
        mViewModel.registerGoogleApiClient(false);
    }


}
