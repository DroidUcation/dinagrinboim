package app.pickage.com.pickage.MessengerActivities;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import app.pickage.com.pickage.ContentProvider.MessengerContentProvider;
import app.pickage.com.pickage.DBHelpers.DBContract;
import app.pickage.com.pickage.R;
import app.pickage.com.pickage.UserActivities.User;

/**
 * Created by משפחת אוביץ on 28/06/2016.
 */
public class FindMessengerIntentService extends IntentService {

    final int NOTIFICATION_ID = 1;
    String fromName = null;

    Messenger messengerFB;
    Messenger currentMessenger;

    public FindMessengerIntentService() {
        super("FindMessengerIntentService");
    }

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    @Override
    protected void onHandleIntent(Intent intent) {
        double latPackage = intent.getDoubleExtra("FROM_LAT", 0);
        double longPackage = intent.getDoubleExtra("FROM_LONG", 0);
        fromName = intent.getStringExtra("FROM_NAME");
//        Uri uri = Uri.withAppendedPath(MessengerContentProvider.CONTENT_URI,"1");
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        getMessengersDetailsFromFireBase();
        if(messengerFB!= null) {
            Toast.makeText(getBaseContext(), "Hello " + messengerFB.getMessengerName(), Toast.LENGTH_LONG).show();
        }

        Cursor cursor = getContentResolver().query(MessengerContentProvider.CONTENT_URI, null, null, null, null);

        double latMessenger;
        double longMessenger;
        float[] results = null;
        float tempRes = Float.MAX_VALUE;
        int position = 0;

        if(cursor != null) {
            while (cursor.moveToNext()) {
                latMessenger = cursor.getDouble(cursor.getColumnIndex(DBContract.MESSENGER_LAT));
                longMessenger = cursor.getDouble(cursor.getColumnIndex(DBContract.MESSENGER_LONG));
                Location.distanceBetween(latPackage, longPackage, latMessenger, longMessenger, results);
                if (results[0] < tempRes) {
                    position = cursor.getPosition();
                    tempRes = results[0];
                }
            }
            cursor.moveToPosition(position);
            cursor.close();
        }
        sendNotification();
    }

    public void getMessengersDetailsFromFireBase(){
//        Firebase.setAndroidContext(this);
//        Firebase ref = new Firebase("https://packme-ea467.firebaseio.com/messengers");
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        };
//        mPostReference.addValueEventListener(postListener);

        Query recentPostsQuery = mDatabase.child("messengers") .limitToFirst(3);
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
