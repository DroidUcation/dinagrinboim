package com.example.android.myfirstapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 13/05/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    /**
     * Alarm receiver to start the pushing new facts service
     */
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, FactsIntentService.class);
            context.startService(i);
        }
}