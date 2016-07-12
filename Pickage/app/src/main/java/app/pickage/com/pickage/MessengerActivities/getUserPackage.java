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

import app.pickage.com.pickage.R;
import app.pickage.com.pickage.UserActivities.FindingMessenger;

public class GetUserPackage extends AppCompatActivity implements View.OnClickListener{

    final int NOTIFICATION_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_package);
        Button btnGetPackage = (Button) findViewById(R.id.btnGetPackage);
        btnGetPackage.setOnClickListener(this);
     }

    @Override
    public void onClick(View v) {
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