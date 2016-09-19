package app.pickage.com.pickage.messengeractivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.pickage.com.pickage.R;

public class MessengerDestinationPath extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    String keyPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_send);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        keyPackage = getIntent().getStringExtra("PACKAGE_KEY");
        Button btnDeliverSuccess = (Button) findViewById(R.id.btnDeliverSuccess);
        btnDeliverSuccess.setOnClickListener(this);
        Button btnDeliverFailed = (Button) findViewById(R.id.btnDeliverFailed);
        btnDeliverFailed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnDeliverSuccess:
                mDatabase.child("packages").child(keyPackage).child("packageStatus").setValue("FINISH");
                i = new Intent(MessengerDestinationPath.this, MessengerMainActivity.class);
                startActivity(i);
                break;
            case R.id.btnDeliverFailed:
                mDatabase.child("packages").child(keyPackage).child("packageStatus").setValue("FAILED");
                i = new Intent(MessengerDestinationPath.this, MessengerMainActivity.class);
                startActivity(i);
                break;
        }
    }
}