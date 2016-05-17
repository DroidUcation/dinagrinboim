package com.example.android.myfirstapplication;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Service to insert new facts to the DB
 */
public class FactsIntentService extends IntentService {

    final int NOTIFICATION_ID = 1;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public FactsIntentService() {
        super("FactsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ContentValues values = new ContentValues();
        Resources res = getResources();
        String factText;
        getContentResolver().delete(FactDBContract.CONTENT_URI, null, null); //delete old facts

        fillTheDB();

        //Send notification
        sendNotification();
    }

    public PendingIntent createPendingIntent() {
        //Add notification action
        Intent resultIntent = new Intent(this, SecondScreen.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        stackBuilder.addParentStack(SecondScreen.class);
        PendingIntent pi = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }

    public void sendNotification() {
        Notification n = new NotificationCompat.Builder(this)
                .setContentTitle("New coke facts")
                .setContentText("5 new facts were added to cokeApp")
                .setSmallIcon(R.drawable.title_img)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(createPendingIntent())
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, n);
    }

    private void fillTheDB() {
        insertFact(1, R.string.new_fact_1_msg);
        insertFact(2, R.string.new_fact_2_msg);
        insertFact(3, R.string.new_fact_3_msg);
        insertFact(4, R.string.new_fact_4_msg);
        insertFact(5, R.string.new_fact_5_msg);
    }

    private void insertFact(int factNumber, int fact) {
        ContentValues values = new ContentValues();
        values.put(FactDBContract.FiveFactsEntry.COLUMN_FACT_NUMBER, factNumber);
        values.put(FactDBContract.FiveFactsEntry.COLUMN_FACT, fact);
        Uri uri = getContentResolver().insert(FactDBContract.CONTENT_URI, values);

    }
}
