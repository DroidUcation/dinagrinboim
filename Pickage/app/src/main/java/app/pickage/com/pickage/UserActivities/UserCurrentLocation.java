package app.pickage.com.pickage.UserActivities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import app.pickage.com.pickage.R;

public class UserCurrentLocation extends FragmentActivity implements View.OnClickListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final int LOCATION_PERMISSION = 583;

    static final int FROM_REQUEST = 1;
    static final int TO_REQUEST = 2;
    static final int ENABLE_GPS_REQUEST = 3;

    private GoogleMap mMap;
    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    TextView fromTxt;
    Marker fromMarker = null;
    TextView toTxt;
    Marker toMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_current_location);
        buildGoogleApiClient();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fromTxt = (TextView) findViewById(R.id.from_text_view);
        fromTxt.setOnClickListener(this);
        fromTxt.setText("Unknown Address");
        toTxt = (TextView) findViewById(R.id.to_text_view);
        toTxt.setOnClickListener(this);
        toTxt.setText("Add destination");
        ((Button)findViewById(R.id.btn_continue_location)).setOnClickListener(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
        } else {
            GetLocation();
        }
    }

    private void GetLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation == null) {
                LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (service.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                else
                    buildAlertMessageNoGps();
            } else {
                if (fromMarker == null) {
                    fromMarker = drawLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), "You are here!");
                    Geocoder geocoder;
                    List<Address> addresses;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        geocoder = new Geocoder(this, Locale.forLanguageTag("en-us"));
                    } else{
                        geocoder = new Geocoder(this, Locale.getDefault());
                    }

                    try {
                        addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                        if (addresses.size() > 0) {
                            String address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName();
                            fromTxt.setText(address);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Marker drawLocation(double latitude, double longitude, String title) {
        LatLng loc = new LatLng(latitude, longitude);
        Marker m = mMap.addMarker(new MarkerOptions().position(loc).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        return m;
    }

    private void removeLocation(Marker marker) {
       if (marker != null)
           marker.remove();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetLocation();
                } else {
                    SetDefaultLocation();
                }
                return;
            }

        }
    }

    private void SetDefaultLocation() {
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), ENABLE_GPS_REQUEST);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                }).setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    @Override
    public void onClick(View v) {
        Intent chooseLocationIntent = new Intent(UserCurrentLocation.this, UserChooseLocation.class);
        switch (v.getId()){
            case R.id.from_text_view:
                startActivityForResult(chooseLocationIntent, FROM_REQUEST);
                break;
            case R.id.to_text_view:
                startActivityForResult(chooseLocationIntent, TO_REQUEST);
                break;
            case R.id.btn_continue_location:
                if (fromMarker != null && toMarker != null) {
                    Intent searchMessangerIntent = new Intent(UserCurrentLocation.this, FillPackageDetails.class);
                    searchMessangerIntent.putExtra("FROM_NAME", fromTxt.getText().toString());
                    searchMessangerIntent.putExtra("FROM_LAT", fromMarker.getPosition().latitude);
                    searchMessangerIntent.putExtra("FROM_LONG", fromMarker.getPosition().longitude);
                    searchMessangerIntent.putExtra("TO_NAME", toTxt.getText().toString());
                    searchMessangerIntent.putExtra("TO_LAT", toMarker.getPosition().latitude);
                    searchMessangerIntent.putExtra("TO_LONG", toMarker.getPosition().longitude);
                    startActivity(searchMessangerIntent);
                } else {
                    toTxt.setError("Add destination is required");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case FROM_REQUEST:
                if (resultCode == RESULT_OK) {
                    fromTxt.setText(data.getStringExtra("NAME"));
                    removeLocation(fromMarker);
                    fromMarker = drawLocation(data.getDoubleExtra("LAT", 0), data.getDoubleExtra("LONG", 0), data.getStringExtra("NAME"));
                }
                break;
            case TO_REQUEST:
                if (resultCode == RESULT_OK) {
                    toTxt.setText(data.getStringExtra("NAME"));
                    removeLocation(toMarker);
                    toMarker = drawLocation(data.getDoubleExtra("LAT", 0), data.getDoubleExtra("LONG", 0), data.getStringExtra("NAME"));
                }
                break;
            case ENABLE_GPS_REQUEST:
                if (resultCode == RESULT_OK) {
                    GetLocation();
                }
                break;
        }

    }
}