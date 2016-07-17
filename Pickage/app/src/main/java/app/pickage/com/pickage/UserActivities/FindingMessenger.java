package app.pickage.com.pickage.UserActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import app.pickage.com.pickage.R;

public class FindingMessenger extends AppCompatActivity implements OnMapReadyCallback{

    EditText getMessengerName;
    EditText getMessengerPhone;
    EditText getMessengerCarType;
    ImageView messengerImgPhoto;
    double mLat;
    double mLong;

    private GoogleMap mMap;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_messenger);

        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://packme-ea467.appspot.com/");

        getMessengerName = (EditText) findViewById(R.id.editMessengerName);
        getMessengerPhone = (EditText) findViewById(R.id.editMessengerPhone);
        getMessengerCarType = (EditText) findViewById(R.id.editMessengerCarType);
        messengerImgPhoto = (ImageView) findViewById(R.id.messengerImgPhoto);
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
        StorageReference messengerRef = storageRef.child("images/messengers/"+ data.getStringExtra("M_KEY") +".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        messengerRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                messengerImgPhoto.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
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