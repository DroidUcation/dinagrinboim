package app.pickage.com.pickage.UserActivities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

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

        nameEditText = (EditText) findViewById(R.id.input_name_signup);
        nameEditText.addTextChangedListener(new AddListenerOnTextChange(this, nameEditText));

        emailEditText = (EditText) findViewById(R.id.input_email_signup);
        emailEditText.addTextChangedListener(new AddListenerOnTextChange(this, emailEditText));

        passEditText = (EditText) findViewById(R.id.input_password_signup);
        passEditText.addTextChangedListener(new AddListenerOnTextChange(this, passEditText));
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

        if(!name.isEmpty() && !email.isEmpty() && !pass.isEmpty()){
            Intent i = new Intent(SingUpUserActivity.this, UploadImg.class);
            startActivity(i);
        }
    }



//            insertInformationToDB();
//            getInformationFromDB();


//            Intent i = new Intent(SingUpUserActivity.this, UserPersonalDetails.class);
//            startActivity(i);
//
//        }
//    }

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
