package app.pickage.com.pickage.MessengerActivities;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import app.pickage.com.pickage.ContentProvider.MessengerContentProvider;
import app.pickage.com.pickage.DBHelpers.DBContract;
import app.pickage.com.pickage.R;
import app.pickage.com.pickage.UserActivities.FindingMessenger;

/**
 * Created by משפחת אוביץ on 28/06/2016.
 */
public class FindMessangerIntentService extends IntentService {

    final int NOTIFICATION_ID = 1;
    String fromName = null;

    public FindMessangerIntentService() {
        super("FindMessangerIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        double latPackage = intent.getDoubleExtra("FROM_LAT", 0);
        double longPackage = intent.getDoubleExtra("FROM_LONG", 0);
        fromName = intent.getStringExtra("FROM_NAME");
        Uri uri = Uri.withAppendedPath(MessengerContentProvider.CONTENT_URI,"1");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        double latMessanger;
        double longMessanger;
        float[] results = null;
        float tempRes = Float.MAX_VALUE;
        int position = 0;

        if(cursor != null) {
            int indexLat = cursor.getColumnIndex(DBContract.MESSENGER_LAT);
            int indexLong = cursor.getColumnIndex(DBContract.MESSENGER_LONG);
            while (cursor.moveToNext()) {
                latMessanger = cursor.getDouble(indexLat);
                longMessanger = cursor.getDouble(indexLong);
                Location.distanceBetween(latPackage, longPackage, latMessanger, longMessanger, results);
                if (results[0] < tempRes) {
                    position = cursor.getPosition();
                    tempRes = results[0];
                }
            }
            cursor.moveToPosition(position);
        }
        sendNotification();
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
