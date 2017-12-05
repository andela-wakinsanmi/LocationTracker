package com.spykins.locationtracker.ui.registrationView;

import android.Manifest;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spykins.locationtracker.AppConstants;
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
    private RegistrationContract.ViewModel mViewModel;
    private PendingIntent mPendingIntent;
    private final int PERMISSION_ACCESS_FINE_LOCATION = 3005;
    private final int SETTINGS_REQUEST_CODE = 3006;
    private static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;


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

        mViewModel = (RegistrationContract.ViewModel) ViewModelProviders.of(this).get(
                RegisterLocationViewModel.class);
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

    private void displayPermissionDialog() {
        ActivityCompat.requestPermissions(this, new String[]{LOCATION_PERMISSION},
                PERMISSION_ACCESS_FINE_LOCATION);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void requestForPermission() {
        if (isAbleToRequestPermission()) {
            if (shouldShowRequestPermissionRationale(LOCATION_PERMISSION)) {
                displayAlertDialog();
            } else {
                displayPermissionDialog();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SETTINGS_REQUEST_CODE) {
            //check again that the device Location is on
            mViewModel.showLocationSettingsIfLocationIsOff();
        }
    }

    private void displayAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.alarm_location_request_title);
        alertDialogBuilder
                .setMessage(R.string.alart_location_request_message)
                .setCancelable(false)
                .setPositiveButton(R.string.alarm_location_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                displayPermissionDialog();
                            }
                        })
                .setNegativeButton(R.string.alarm_location_no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                closeApp();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void closeApp() {
        finish();
    }

    private boolean isAbleToRequestPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If permission is accepted check if location is on or off
                    mViewModel.showLocationSettingsIfLocationIsOff();

                } else {
                    closeApp();
                }
                break;
        }
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

    @Override
    public void startSettingsActivity() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, SETTINGS_REQUEST_CODE);
    }

    @Override
    public boolean isFineLocationPermissionGranted() {
        if (isAbleToRequestPermission()) {
            return ContextCompat.checkSelfPermission(this, LOCATION_PERMISSION)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
}
