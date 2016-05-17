package com.example.android.myfirstapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class FirstScreen extends AppCompatActivity {

    private boolean dataInserted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        // initialize facts table
        if(!dataInserted){
           InsertFactsToDB();
            dataInserted = true; // insert data only once.TODO: need to save in Bundle savedInstanceState
        }

        ImageView screentwo = (ImageView) findViewById(R.id.background_afs_image_view);
        screentwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstScreen.this, SecondScreen.class);
                TextView userName = (TextView) findViewById(R.id.edit_afs_text_view);

                if (userName.getText().toString().length() == 0) {
                    userName.setError("First name is required!");
                } else {
                    intent.putExtra("userName", userName.getText().toString());
                    startActivity(intent);
                }
            }
        });

        setAlarm();
    }

    /**
     * Set alarm that be called every day
     */
    public void setAlarm(){
        Context cnx = getBaseContext();

        //Create calander
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.PM);

        Intent intent = new Intent(cnx, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(cnx, 0, intent, 0);
        AlarmManager am = (AlarmManager) cnx.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 6 * 1000, pendingIntent);
    }

    private void InsertFactsToDB(){
        insertFact(1, R.string.fact_1_msg);
        insertFact(2, R.string.fact_2_msg);
        insertFact(3,R.string.fact_3_msg);
        insertFact(4, R.string.fact_4_msg);
        insertFact(5, R.string.fact_5_msg);
    }

    private void insertFact(int factNumber,int fact){
        ContentValues values = new ContentValues();
        values.put(FactDBContract.FiveFactsEntry.COLUMN_FACT_NUMBER,factNumber);
        values.put(FactDBContract.FiveFactsEntry.COLUMN_FACT, fact);
        Uri uri = getContentResolver().insert(FactDBContract.CONTENT_URI,values);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}