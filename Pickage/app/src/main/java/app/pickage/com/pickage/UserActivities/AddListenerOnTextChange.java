package app.pickage.com.pickage.UserActivities;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.pickage.com.pickage.R;

/**
 * Created by User on 17/06/2016.
 */
public class AddListenerOnTextChange implements TextWatcher {
    private Context mContext;
    EditText mEdittextview;

    public AddListenerOnTextChange(Context context, EditText edittextview) {
        super();
        this.mContext = context;
        this.mEdittextview = edittextview;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

//    private boolean isValidContactOrigionPackage(String contactOrigionPackage) {
//        String CONTACT_ORIGION_PACKAGE = "^\\+[0-9]{10,13}$";
//
//        Pattern pattern = Pattern.compile(CONTACT_ORIGION_PACKAGE);
//        Matcher matcher = pattern.matcher(contactOrigionPackage);
//        return matcher.matches();
//    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void afterTextChanged(Editable s) {
            switch (mEdittextview.getId()) {
                case R.id.email_login:
                    final String loginEmail = mEdittextview.getText().toString();
                    if (!isValidEmail(loginEmail)) {
                        mEdittextview.setError("Invalid Email");
                    }
                    break;
                case R.id.password:
                    final String loginPass = mEdittextview.getText().toString();
                    if (!isValidPassword(loginPass)) {
                        mEdittextview.setError("Invalid Password - password smoller then 7 char");
                    }
                    break;
                case R.id.input_email_signup:
                    final String email = mEdittextview.getText().toString();
                    if (!isValidEmail(email)) {
                        mEdittextview.setError("Invalid Email");
                    }
                    break;
                case R.id.input_password_signup:
                    final String pass = mEdittextview.getText().toString();
                    if (!isValidPassword(pass)) {
                        mEdittextview.setError("Invalid Password - password smoller then 6 char");
                    }
                    break;
//                case R.id.editContactOrigionPackage:
//                    final String ContactOrigionPackage = mEdittextview.getText().toString();
//                    if (!isValidContactOrigionPackage(ContactOrigionPackage)) {
//                        mEdittextview.setError("Invalid Contact Origion Package");
//                    }
//                    break;
//                case R.id.editContactDestinationPackage:
//                    final String ContactDestinationPackage = mEdittextview.getText().toString();
//                    if (!isValidContactOrigionPackage(ContactDestinationPackage)) {
//                        mEdittextview.setError("Invalid Contact Destination Package");
//                    }
//                    break;
            }
        }
}