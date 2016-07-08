package app.pickage.com.pickage.UserActivities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import app.pickage.com.pickage.ContentProvider.MessengerContentProvider;
import app.pickage.com.pickage.ContentProvider.UserContentProvider;
import app.pickage.com.pickage.DBHelpers.DBContract;
import app.pickage.com.pickage.MessengerActivities.Messenger;
import app.pickage.com.pickage.R;

public class SingUpUserActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;
    private EditText nameEditText;
    private String name;
    private String email;
    private String pass;

    private List<User> userList = new ArrayList<>();

    private TextView testLogin;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
// [END declare_database_ref]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_user2);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]


        nameEditText = (EditText) findViewById(R.id.input_name_signup);
        nameEditText.addTextChangedListener(new AddListenerOnTextChange(this, nameEditText));

        emailEditText = (EditText) findViewById(R.id.input_email_signup);
        emailEditText.addTextChangedListener(new AddListenerOnTextChange(this, emailEditText));

        passEditText = (EditText) findViewById(R.id.input_password_signup);
        passEditText.addTextChangedListener(new AddListenerOnTextChange(this, passEditText));

        testLogin = (TextView) findViewById(R.id.testLogin);
    }

    public void continueSingUpBtn(View view) {
        final String name = nameEditText.getText().toString();
        if (name.trim().length() == 0 ) {
            nameEditText.setError("Name filed is required");
        }
        final String email = emailEditText.getText().toString();
        if (emailEditText.getText().toString().trim().length() == 0) {
            emailEditText.setError("Email filed is required");
        }
        final String pass = passEditText.getText().toString();
        if(passEditText.getText().toString().trim().length() == 0){
            passEditText.setError("Password filed is required");
        }

        Intent intent = getIntent();
        if(intent.getBooleanExtra("isMessenger", Boolean.parseBoolean("false"))){
            User user = new User(nameEditText.getText().toString(),emailEditText.getText().toString(),passEditText.getText().toString());
            saveUserOnFireBase(user);
        }



        else if(intent.getBooleanExtra("isMessenger", Boolean.parseBoolean("true"))){
            Messenger messenger = new Messenger(nameEditText.getText().toString(),emailEditText.getText().toString(),passEditText.getText().toString());
            saveMessengerOnFireBase(messenger);
            insertMessenger();
            getMessengerFromDB(); // for check insert messenger
        }

        if(!name.isEmpty() && !email.isEmpty() && !pass.isEmpty()){
            Intent i = new Intent(SingUpUserActivity.this, UploadImg.class);
            startActivity(i);
        }
    }

    public void saveUserOnFireBase(User user) {
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://packme-ea467.firebaseio.com/users");
        //Storing values to firebase
        ref = ref.push();
        ref.setValue(user);
        String userKey = ref.getKey();
    }

    public void saveMessengerOnFireBase(Messenger messenger) {
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://packme-ea467.firebaseio.com/messengers");
        //Storing values to firebase
        ref = ref.push();
        ref.setValue(messenger);
        String userKey = ref.getKey();
    }

    public void insertMessenger(){
        // Add a new messenger record
        ContentValues values = new ContentValues();
        values.put(DBContract.MESSENGER_NAME,
            ((EditText) findViewById(R.id.input_name_signup)).getText().toString());
        values.put(DBContract.MESSENGER_MAIL,
            ((EditText) findViewById(R.id.input_email_signup)).getText().toString());
        values.put(DBContract.MESSENGER_PASSWORD,
            ((EditText) findViewById(R.id.input_password_signup)).getText().toString());
        Uri uri = getContentResolver().insert(MessengerContentProvider.CONTENT_URI, values);
    }
    private void getMessengerFromDB() {
        Uri uri = Uri.withAppendedPath(MessengerContentProvider.CONTENT_URI, "messenger");
        Cursor c = getContentResolver().query(MessengerContentProvider.CONTENT_URI, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do{
                testLogin.setText(c.getString(c.getColumnIndex(DBContract.MESSENGER_NAME)));
            }
            while (c.moveToNext());
        }
    }

    public void loginBtn(View view) {
        Intent i = new Intent(SingUpUserActivity.this, LoginUserActivity.class);
        startActivity(i);
    }

    private void insertUser() {
        // Add a new user record
        ContentValues values = new ContentValues();
        values.put(DBContract.USER_NAME,
                ((EditText) findViewById(R.id.input_name_signup)).getText().toString());
        values.put(DBContract.USER_MAIL,
                ((EditText) findViewById(R.id.input_email_signup)).getText().toString());
        values.put(DBContract.USER_PASSSWORD,
                ((EditText) findViewById(R.id.input_password_signup)).getText().toString());
        Uri uri = getContentResolver().insert(UserContentProvider.CONTENT_URI, values);
    }
    private void getUserFromDB() {
        Uri uri = Uri.withAppendedPath(UserContentProvider.CONTENT_URI, "user");
        Cursor c = getContentResolver().query(UserContentProvider.CONTENT_URI, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do{
                testLogin.setText(c.getString(c.getColumnIndex(DBContract.USER_NAME)));
            }
            while (c.moveToNext());
        }
    }
}
