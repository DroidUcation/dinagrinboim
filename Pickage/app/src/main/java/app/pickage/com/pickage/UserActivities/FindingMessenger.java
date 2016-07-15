package app.pickage.com.pickage.UserActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import app.pickage.com.pickage.R;

public class FindingMessenger extends AppCompatActivity implements OnMapReadyCallback{

    EditText getMessengerName;
    EditText getMessengerPhone;
    EditText getMessengerCarType;
    double mLat;
    double mLong;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_messenger);

        getMessengerName = (EditText) findViewById(R.id.editMessengerName);
        getMessengerPhone = (EditText) findViewById(R.id.editMessengerPhone);
        getMessengerCarType = (EditText) findViewById(R.id.editMessengerCarType);
        getMessengerDetails(getIntent());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.routeMessengerToUser);
        mapFragment.getMapAsync(this);
    }

    private void getMessengerDetails(Intent data) {
        getMessengerName.setText(data.getStringExtra("M_NAME"));
        getMessengerPhone.setText(data.getStringExtra("M_PHONE"));
        getMessengerCarType.setText(data.getStringExtra("M_CAR"));
        mLat = data.getDoubleExtra("M_LAT", 0);
        mLong = data.getDoubleExtra("M_LONG", 0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawLocation(mLat, mLong, "Messenger");
    }

    private Marker drawLocation(double latitude, double longitude, String title) {
        LatLng loc = new LatLng(latitude, longitude);
        Marker m = mMap.addMarker(new MarkerOptions().position(loc).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        return m;
    }
}