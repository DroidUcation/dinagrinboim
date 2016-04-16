package com.example.android.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Myth1Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myth1_screen);

        Intent intent = getIntent();
        TextView message = (TextView) findViewById(R.id.screen_myth_text_view);
        message.setText(R.string.myth_1_msg);
        message.setTextSize(16);
    }
}
