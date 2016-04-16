package com.example.android.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Fact5Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact5_screen);

        Intent intent = getIntent();
        TextView message = (TextView) findViewById(R.id.screen_fact_text_view);
        message.setText(R.string.fact_5_msg);
        message.setTextSize(16);
    }
}
