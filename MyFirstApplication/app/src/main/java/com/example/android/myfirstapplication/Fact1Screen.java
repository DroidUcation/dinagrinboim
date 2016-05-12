package com.example.android.myfirstapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Fact1Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact1_screen);

       Intent intent = getIntent();

        Uri uri = Uri.withAppendedPath(FactDBContract.CONTENT_URI,"1");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if(cursor.moveToFirst())
        {
            String fact = cursor.getString(2);
            TextView view = (TextView) findViewById(R.id.screen_fact_text_view);
            view.setText(Integer.parseInt(fact));

        }
    }
}
