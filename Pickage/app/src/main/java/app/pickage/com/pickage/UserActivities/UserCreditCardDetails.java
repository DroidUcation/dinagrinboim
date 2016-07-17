package app.pickage.com.pickage.useractivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.pickage.com.pickage.R;

public class UserCreditCardDetails extends AppCompatActivity{

     EditText creditCardNumber;
     EditText creditCardType;
     EditText creditCardName;
     EditText creditCardDate;
     EditText creditCardCSV;
     String keyUser;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_credit_card_details);

        creditCardNumber = (EditText)findViewById(R.id.editNumber);
        creditCardType = (EditText)findViewById(R.id.editType);
        creditCardName = (EditText)findViewById(R.id.editName);
        creditCardDate = (EditText)findViewById(R.id.editDate);
        creditCardCSV = (EditText)findViewById(R.id.editCsv);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
    }

    public void creditCardDetailsBtnNext(View view) {
        Intent grtIntent = getIntent();
        keyUser = grtIntent.getStringExtra("USER_KEY");
        mDatabase.child("users").child(keyUser).child("creditCardNumber").setValue(creditCardNumber.getText().toString());
        mDatabase.child("users").child(keyUser).child("creditCardType").setValue(creditCardType.getText().toString());
        mDatabase.child("users").child(keyUser).child("creditCardName").setValue(creditCardName.getText().toString());
        mDatabase.child("users").child(keyUser).child("creditCardDate").setValue(creditCardDate.getText().toString());
        mDatabase.child("users").child(keyUser).child("creditCardCSV").setValue(creditCardCSV.getText().toString());
        Intent intent = new Intent(UserCreditCardDetails.this, LoginActivity.class);
        startActivity(intent);
    }
}