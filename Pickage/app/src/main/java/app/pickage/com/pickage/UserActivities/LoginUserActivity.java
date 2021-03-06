package app.pickage.com.pickage.UserActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import app.pickage.com.pickage.R;

public class LoginUserActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user2);

        emailEditText = (EditText) findViewById(R.id.email_login);
        emailEditText.addTextChangedListener(new AddListenerOnTextChange(this, emailEditText));

        passEditText = (EditText) findViewById(R.id.password);
        passEditText.addTextChangedListener(new AddListenerOnTextChange(this, passEditText));
    }

    private boolean isValidLoginInput(View view) {
        final String email = emailEditText.getText().toString();
        if (emailEditText.getText().toString().trim().length() == 0) {
            emailEditText.setError("Email filed is required");
            return false;
        }
        final String pass = passEditText.getText().toString();
        if(passEditText.getText().toString().trim().length() == 0){
            passEditText.setError("Password filed is required");
            return false;
        }
        return true;
    }

    public void continueBtn(View view) {
        if(isValidLoginInput(view)){
            Intent i = new Intent(LoginUserActivity.this, UserCurrentLocation.class);
            startActivity(i);
        }
    }

    public void createAccount(View view) {
        Intent i = new Intent(LoginUserActivity.this, SingUpUserActivity.class);
        startActivity(i);
    }
}