package app.pickage.com.pickage.MessengerActivities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.pickage.com.pickage.R;
import app.pickage.com.pickage.UserActivities.FindingMessenger;

public class GetUserPackage extends AppCompatActivity implements View.OnClickListener,OnMapReadyCallback {

    final int NOTIFICATION_ID = 2;
    private DatabaseReference mDatabase;
    String keyPackage;
    double mLat;
    double mLong;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_package);
        Button btnGetPackage = (Button) findViewById(R.id.btnGetPackage);
        btnGetPackage.setOnClickListener(this);
        Button btnDeclinePackage = (Button) findViewById(R.id.btnDeclinePackage);
        btnDeclinePackage.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.packageFrom);
        mapFragment.getMapAsync(this);

        Intent data = getIntent();
        keyPackage = data.getStringExtra("PACKAGE_KEY");
        mLat = data.getDoubleExtra("FROM_LAT", 0);
        mLong = data.getDoubleExtra("FROM_LONG", 0);
        EditText editOriginPackageForMess = (EditText) findViewById(R.id.editOriginPackageForMess);
        editOriginPackageForMess.setText(data.getStringExtra("FROM_NAME"));
        EditText editOriginContactForMess = (EditText) findViewById(R.id.editOriginContactForMess);
        editOriginContactForMess.setText(data.getStringExtra("FROM_CONTACT"));
        EditText editDestinationPackageForMess = (EditText) findViewById(R.id.editDestinationPackageForMess);
        editDestinationPackageForMess.setText(data.getStringExtra("TO_NAME"));
        EditText editDestinationContactForMess = (EditText) findViewById(R.id.editDestinationContactForMess);
        editDestinationContactForMess.setText(data.getStringExtra("TO_CONTACT"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawLocation(mLat, mLong, "Collect Package");
    }

    private Marker drawLocation(double latitude, double longitude, String title) {
        LatLng loc = new LatLng(latitude, longitude);
        Marker m = mMap.addMarker(new MarkerOptions().position(loc).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        return m;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetPackage:
                mDatabase.child("packages").child(keyPackage).child("packageStatus").setValue("ACCEPT");
                Intent intent = new Intent(GetUserPackage.this, MessengerDestinationPath.class);
                Intent data = getIntent();
                intent.putExtras(data);
                intent.putExtra("PACKAGE_KEY", keyPackage);
                startActivity(intent);
                break;
            case R.id.btnDeclinePackage:
                Toast.makeText(GetUserPackage.this,
                        String.valueOf("Please contact with the sender."),
                        Toast.LENGTH_LONG).show();
                mDatabase.child("packages").child(keyPackage).child("packageStatus").setValue("DECLINE");
                Intent i = new Intent(GetUserPackage.this, MessengerMainActivity.class);
                startActivity(i);
                break;
        }
    }

    public PendingIntent createPendingIntent() {
        //Add notification action
        Intent resultIntent = new Intent(this, FindingMessenger.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        stackBuilder.addParentStack(FindingMessenger.class);
        PendingIntent pi = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }

    public void sendNotification() {
        PendingIntent pIntent = createPendingIntent();
        Notification n = new NotificationCompat.Builder(this)
                .setContentTitle("Messenger is coming")
                .setContentText("The messenger is on his way")
                .setSmallIcon(R.drawable.open4)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, n);
    }
}