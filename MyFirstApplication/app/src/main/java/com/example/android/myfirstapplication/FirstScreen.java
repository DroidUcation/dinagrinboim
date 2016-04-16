package com.example.android.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        ImageView screentwo = (ImageView) findViewById(R.id.background_afs_image_view);
        screentwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstScreen.this,SecondScreen.class);
                TextView userName = (TextView) findViewById(R.id.edit_afs_text_view);

                if( userName.getText().toString().length() == 0 ){
                    userName.setError( "First name is required!" );
                }else{
                    intent.putExtra ( "userName", userName.getText().toString() );
                    startActivity(intent);
                }
            }
        });

    }
}
