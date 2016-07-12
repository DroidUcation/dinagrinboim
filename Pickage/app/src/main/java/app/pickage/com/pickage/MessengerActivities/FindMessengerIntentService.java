package app.pickage.com.pickage.MessengerActivities;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.pickage.com.pickage.R;
import app.pickage.com.pickage.UserActivities.Feedback;
import app.pickage.com.pickage.UserActivities.FillPackageDetails;
import app.pickage.com.pickage.UserActivities.FindingMessenger;

/**
 * Created by משפחת אוביץ on 28/06/2016.
 */
public class FindMessengerIntentService extends IntentService {

    final int NOTIFICATION_ID = 1;
    String fromName = null;
    private DatabaseReference mDatabase;
    double latPackage;
    double longPackage;
    String keyPackage;
    Messenger nearestMessenger;

    public FindMessengerIntentService() {
        super("FindMessengerIntentService");
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        latPackage = intent.getDoubleExtra("FROM_LAT", 0);
        longPackage = intent.getDoubleExtra("FROM_LONG", 0);
        fromName = intent.getStringExtra("FROM_NAME");
        keyPackage = intent.getStringExtra("PACKAGE_KEY");

        mDatabase.child("messengers").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get messenger value
                        Map<String,Messenger> messengers = (HashMap<String,Messenger>)dataSnapshot.getValue();
                        float res;
                        float tempRes = 0;
                        String tempKey = null;
                        Messenger messenger;

                        if (messengers != null) {
                            for (String key : messengers.keySet()) {
//                                messenger = messengers.get(key);
                                messenger = dataSnapshot.child(key).getValue(Messenger.class);
                                double latMessenger = messenger.getMessengerLat();
                                double longMessenger = messenger.getMessengerLong();
                                res = getDistance(latMessenger, longMessenger);
                                if (tempRes == 0 || res < tempRes) {
                                    tempRes = res;
                                    tempKey = key;
                                    nearestMessenger = messenger;
                                }
                            }
                            mDatabase.child("packages").child(keyPackage).child("pMessengerID").setValue(tempKey);
//                            sendNotification();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

        mDatabase.child("packages").child(keyPackage).child("packageStatus").addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get status value
                    String status = dataSnapshot.getValue(String.class);
                    switch (status) {
                        case "ACCEPT":
                            Intent findMessengerIntent = new Intent(getBaseContext(), FindingMessenger.class);
                            findMessengerIntent.putExtra("M_NAME", nearestMessenger.getMessengerName());
                            findMessengerIntent.putExtra("M_PHONE", nearestMessenger.getMessengerPhone());
                            findMessengerIntent.putExtra("M_CAR", nearestMessenger.getMessengerCarType());
                            findMessengerIntent.putExtra("M_LAT", nearestMessenger.getMessengerLat());
                            findMessengerIntent.putExtra("M_LONG", nearestMessenger.getMessengerLong());
                            findMessengerIntent.putExtra("M_IMG", nearestMessenger.getMessengerImg());
                            findMessengerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplication().startActivity(findMessengerIntent);
                            break;
                        case "FINISH":
                            Intent feedbackIntent = new Intent(getBaseContext(), Feedback.class);
                            feedbackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplication().startActivity(feedbackIntent);
                            break;
                    }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });


//        Uri uri = Uri.withAppendedPath(MessengerContentProvider.CONTENT_URI,"1");
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        Cursor cursor = getContentResolver().query(MessengerContentProvider.CONTENT_URI, null, null, null, null);
//
//        double latMessenger;
//        double longMessenger;
//        float[] results = null;
//        float tempRes = Float.MAX_VALUE;
//        int position = 0;
//
//
//
//        if(cursor != null) {
//            while (cursor.moveToNext()) {
//                latMessenger = cursor.getDouble(cursor.getColumnIndex(DBContract.MESSENGER_LAT));
//                longMessenger = cursor.getDouble(cursor.getColumnIndex(DBContract.MESSENGER_LONG));
//                Location.distanceBetween(latPackage, longPackage, latMessenger, longMessenger, results);
//                if (results[0] < tempRes) {
//                    position = cursor.getPosition();
//                    tempRes = results[0];
//                }
//            }
//            cursor.moveToPosition(position);
//            cursor.close();
//        }
//        sendNotification();
    }

    private float getDistance(double latMessenger , double longMessenger) {
        Location locationA = new Location("LocationPackage");
        locationA.setLatitude(latPackage);
        locationA.setLongitude(longPackage);

        Location locationB = new Location("LocationMessenger");
        locationB.setLatitude(latMessenger);
        locationB.setLongitude(longMessenger);

        float distance = locationA.distanceTo(locationB);
        return distance;
    }

    public PendingIntent createPendingIntent() {
        //Add notification action
        Intent resultIntent = new Intent(this, GetUserPackage.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        stackBuilder.addParentStack(GetUserPackage.class);
        PendingIntent pi = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }

    public void sendNotification() {
        PendingIntent pIntent = createPendingIntent();
        Notification n = new NotificationCompat.Builder(this)
                .setContentTitle("New Package at " + fromName)
                .setContentText("You have a new package. Are you ready?")
                .setSmallIcon(R.drawable.open4)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .addAction(R.drawable.open4, "Accept", pIntent)
                .addAction(R.drawable.open4, "Decline", pIntent)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, n);
    }
}
