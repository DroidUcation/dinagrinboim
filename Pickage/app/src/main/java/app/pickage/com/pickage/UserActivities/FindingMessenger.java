package app.pickage.com.pickage.UserActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import app.pickage.com.pickage.R;

public class FindingMessenger extends AppCompatActivity {

    EditText getMessengerName;
    EditText getMessengerPhone;
    EditText getMessengerCardType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_messenger);

        getMessengerName = (EditText) findViewById(R.id.editMessengerName);
        getMessengerPhone = (EditText) findViewById(R.id.editMessengerPhone);
        getMessengerCardType = (EditText) findViewById(R.id.editMessengerCardType);
        getMessengerDetails(getMessengerName,getMessengerPhone,getMessengerCardType);
    }

    private void getMessengerDetails(EditText getMessengerName, EditText getMessengerPhone, EditText getMessengerCardType) {


    }


}
