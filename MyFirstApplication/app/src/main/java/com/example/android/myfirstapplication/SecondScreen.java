package com.example.android.myfirstapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondScreen extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        Intent intent = getIntent();
        String text = intent.getStringExtra("userName");
        TextView textUserName = (TextView) findViewById(R.id.userName_ass_text_view);
        textUserName.setText(text);

        ImageView myth1 = (ImageView)findViewById(R.id.myth1_ass_image_view);
        myth1.setOnClickListener( this);
        ImageView myth2 = (ImageView)findViewById(R.id.myth2_ass_image_view);
        myth2.setOnClickListener(this);
        ImageView myth3 = (ImageView)findViewById(R.id.myth3_ass_image_view);
        myth3.setOnClickListener(this);
        ImageView myth4 = (ImageView)findViewById(R.id.myth4_ass_image_view);
        myth4.setOnClickListener(this);
        ImageView myth5 = (ImageView)findViewById(R.id.myth5_ass_image_view);
        myth5.setOnClickListener(this);
        ImageView fact1 = (ImageView)findViewById(R.id.fact1_ass_image_view);
        fact1.setOnClickListener(this);
        ImageView fact2 = (ImageView)findViewById(R.id.fact2_ass_image_view);
        fact2.setOnClickListener(this);
        ImageView fact3 = (ImageView)findViewById(R.id.fact3_ass_image_view);
        fact3.setOnClickListener(this);
        ImageView fact4 = (ImageView)findViewById(R.id.fact4_ass_image_view);
        fact4.setOnClickListener(this);
        ImageView fact5 = (ImageView)findViewById(R.id.fact5_ass_image_view);
        fact5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch(id){
            case R.id.myth1_ass_image_view:
                intent = new Intent(SecondScreen.this, Myth1Screen.class);
                startActivity(intent);
                break;
            case R.id.myth2_ass_image_view:
                intent = new Intent(SecondScreen.this, Myth2Screen.class);
                startActivity(intent);
                break;
            case R.id.myth3_ass_image_view:
                intent = new Intent(SecondScreen.this, Myth3Screen.class);
                startActivity(intent);
                break;
            case R.id.myth4_ass_image_view:
                intent = new Intent(SecondScreen.this, Myth4Screen.class);
                startActivity(intent);
                break;
            case R.id.myth5_ass_image_view:
                intent = new Intent(SecondScreen.this, Myth5Screen.class);
                startActivity(intent);
                break;
            case R.id.fact1_ass_image_view:
                intent = new Intent(SecondScreen.this, Fact1Screen.class);
                startActivity(intent);
                break;
            case R.id.fact2_ass_image_view:
                intent = new Intent(SecondScreen.this, Fact2Screen.class);
                startActivity(intent);
                break;
            case R.id.fact3_ass_image_view:
                intent = new Intent(SecondScreen.this, Fact3Screen.class);
                startActivity(intent);
                break;
            case R.id.fact4_ass_image_view:
                intent = new Intent(SecondScreen.this, Fact4Screen.class);
                startActivity(intent);
                break;
            case R.id.fact5_ass_image_view:
                intent = new Intent(SecondScreen.this, Fact5Screen.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
