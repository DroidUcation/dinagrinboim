package app.pickage.com.pickage.UserActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.pickage.com.pickage.R;

//import app.pickage.com.pickage.R;

public class LoginUserActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user2);
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

    public boolean checkInput(){
        emailEditText = (EditText) findViewById(R.id.email);
        passEditText = (EditText) findViewById(R.id.password);

        final String email = emailEditText.getText().toString();
        if(emailEditText.getText().toString().trim().length() == 0){
            emailEditText.setError("Email filed is required");
            return false;
        } else if (!isValidEmail(email)) {
            emailEditText.setError("Invalid Email");
            return false;
        }

        final String pass = passEditText.getText().toString();
        if(passEditText.getText().toString().trim().length() == 0){
            passEditText.setError("Password filed is required");
        return false;}
        else if (!isValidPassword(pass)) {
            passEditText.setError("Invalid Password - password smoller then 6 char");
            return false;
        }
        return true;
    }

    public void continueBtn(View view) {
        if(checkInput() == true){
            Intent i = new Intent(LoginUserActivity.this, UserCurrentLocation.class);
            startActivity(i);
        }
    }

    public void createAccount(View view) {
        Intent i = new Intent(LoginUserActivity.this, SingUpUserActivity.class);
        startActivity(i);
    }
}
