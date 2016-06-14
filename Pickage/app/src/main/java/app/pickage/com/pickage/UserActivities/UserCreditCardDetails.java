package app.pickage.com.pickage.UserActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

import app.pickage.com.pickage.R;

public class UserCreditCardDetails extends AppCompatActivity implements TextWatcher {

    private String creditCardNumber;
    private String creditCardType;
    private String creditCardDate;
    private String creditCardCSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_credit_card_details);

//        EditText creditCardNumber = (EditText) findViewById(R.id)
    }

    public void check(){



    }


//    ArrayList<String> listOfPattern=new ArrayList<String>();
//
//    String ptVisa = "^4[0-9]{6,}$";
//    listOfPattern.add(ptVisa);
//    String ptMasterCard = "^5[1-5][0-9]{5,}$";
//    listOfPattern.add(ptMasterCard);
//    String ptAmeExp = "^3[47][0-9]{5,}$";
//    listOfPattern.add(ptAmeExp);
//    String ptDinClb = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";
//    listOfPattern.add(ptDinClb);
//    String ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
//    listOfPattern.add(ptDiscover);
//    String ptJcb = "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$";
//    listOfPattern.add(ptJcb);

    // Change this to what you want... ' ', '-' etc..
    private static final char space = ' ';

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Remove spacing char
        if (s.length() > 0 && (s.length() % 5) == 0) {
            final char c = s.charAt(s.length() - 1);
            if (space == c) {
                s.delete(s.length() - 1, s.length());
            }
        }
        // Insert char where needed.
        if (s.length() > 0 && (s.length() % 5) == 0) {
            char c = s.charAt(s.length() - 1);
            // Only if its a digit where there should be a space we insert a space
            if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                s.insert(s.length() - 1, String.valueOf(space));
            }
        }
    }
}
