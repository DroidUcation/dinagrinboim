package app.pickage.com.pickage.UserActivities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import com.google.android.gms.analytics.ecommerce.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.pickage.com.pickage.ContentProvider.UserContentProvider;
import app.pickage.com.pickage.DBHelpers.DBContract;
import app.pickage.com.pickage.R;

public class SingUpUserActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;
    private EditText nameEditText;
    private String name;
    private String email;
    private String pass;

    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_user2);

//        nameEditText = (EditText) findViewById(R.id.input_name);
//        emailEditText = (EditText) findViewById(R.id.input_email);
//        passEditText = (EditText) findViewById(R.id.input_password);
//        name = nameEditText.getText().toString();
//        email = emailEditText.getText().toString();
//        pass = passEditText.getText().toString();
    }

    // validating email id

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    // validating name not null
    private boolean isValidName(String name) {
        if (name.trim().length() > 0 ){
            return true;
        }
        return false;
    }

    public boolean checkInput() {
        nameEditText = (EditText) findViewById(R.id.input_name_signup);
        name = nameEditText.getText().toString();
        if (!isValidName(name)) {
            nameEditText.setError("Name filed is required");
            return false;
        }

        emailEditText = (EditText) findViewById(R.id.input_email_signup);
        email = emailEditText.getText().toString();
        if (emailEditText.getText().toString().trim().length() == 0) {
            emailEditText.setError("Email filed is required");
            return false;
        } else if (!isValidEmail(email)) {
            emailEditText.setError("Invalid Email");
            return false;
        }

        passEditText = (EditText) findViewById(R.id.input_password_signup);
        pass = passEditText.getText().toString();
        if (passEditText.getText().toString().trim().length() == 0) {
            passEditText.setError("Password filed is required");
            return false;
        } else if (!isValidPassword(pass)) {
            passEditText.setError("Invalid Password - password smoller then 6 char");
            return false;
        }
        return true;
    }

    public void continueSingUpBtn(View view) {
        if (checkInput() == true) {




//            insertInformationToDB();
//            getInformationFromDB();


//            Intent i = new Intent(SingUpUserActivity.this, UserPersonalDetails.class);
//            startActivity(i);

        }
    }

    public void loginBtn(View view) {
        Intent i = new Intent(SingUpUserActivity.this, LoginUserActivity.class);
        startActivity(i);
    }

    private void insertInformationToDB() {
        User user = new User(name,email,pass);
        user.setUserName(name);
        user.setUserEmail(email);
        user.setUserPassword(pass);

        ContentValues values = new ContentValues();
        values.put(DBContract.USER_NAME, name);
        values.put(DBContract.USER_MAIL, email);
        values.put(DBContract.USER_PASSSWORD, pass);
        Uri uri = getContentResolver().insert(UserContentProvider.CONTENT_URI, values);
    }

    /**
     * Get users list by users contentProvider
     */
    private void getInformationFromDB() {
        Uri uri = Uri.withAppendedPath(UserContentProvider.CONTENT_URI, "user");
        Cursor c = getContentResolver().query(UserContentProvider.CONTENT_URI, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do{
                User user = new User();
                user.setUserName(c.getString(c.getColumnIndex(DBContract.USER_NAME)));
                user.setUserEmail(c.getString(c.getColumnIndex(DBContract.USER_MAIL)));
                user.setUserPassword(c.getString(c.getColumnIndex(DBContract.USER_PASSSWORD)));
                user.setUserID(c.getInt(c.getColumnIndex(DBContract.USER_ID)));
                userList.add(user);
            }
            while (c.moveToNext());
        }
    }

    //check me content provider
//    private void getInformationFromDB() {
//        Uri uri = Uri.withAppendedPath(UserContentProvider.CONTENT_URI, "user");
//        Cursor c = getContentResolver().query(UserContentProvider.CONTENT_URI, null, null, null,null);
//        if (c != null && c.moveToFirst()) {
//            do{
//                User user = new User();
//                user.setUserName(c.getString(c.getColumnIndex(DBContract.USER_NAME)));
//                user.setUserEmail(c.getString(c.getColumnIndex(DBContract.USER_MAIL)));
//                user.setUserPassword(c.getString(c.getColumnIndex(DBContract.USER_PASSSWORD)));
//                TextView t = (TextView) findViewById(R.id.tmp);
//                t.setText(user.getUserPassword());
//                //user.setUserID(c.getInt(c.getColumnIndex(DBContract.USER_ID)));
//                userList.add(user);
//            }
//            while (c.moveToNext());
//        }
//    }
}




//        phoneEditText = (EditText) findViewById(R.id.phone);
//        userNameEditText = (EditText) findViewById(R.id.userName);
//
//        findViewById(R.id.continue_btn).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                final String phone = phoneEditText.getText().toString();
//                if (!isValidPhone(phone)) {
//                    phoneEditText.setError("Invalid Phone");
//                }
//                final String name = userNameEditText.getText().toString();
//                if (!isValidName(name)) {
//                    userNameEditText.setError("Input Name");
//                }
//            }
//        });
//    }
//
//    // validating phone
//    private boolean isValidPhone(String phone) {
//        String PHONE_PATTERN = "^[+][0-9]{10,13}$";
//
//        Pattern pattern = Pattern.compile(PHONE_PATTERN);
//        Matcher matcher = pattern.matcher(phone);
//        return matcher.matches();
//    }
//
//    // validating user name
//    private boolean isValidName(String name) {
//        if (name != null) {
//            return true;
//        }
//        return false;
//    }
