package app.pickage.com.pickage.UserActivities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.pickage.com.pickage.R;

import java.util.Calendar;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class FillPackageDetails extends AppCompatActivity {

    private TextView tvDisplayTime;
    private TimePicker timePicker1;
    private Button btnChangeTime;

    private int hour;
    private int minute;

    static final int TIME_DIALOG_ID = 999;

    private EditText contactOrigionPackage;
    private EditText contactDestinationPackage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_package_details);

        setCurrentTimeOnView();
        addListenerOnButton();

        TextView editFrom = (TextView) findViewById(R.id.editFrom);
        TextView editTo = (TextView) findViewById(R.id.editTo);
        Intent data = getIntent();

        editFrom.setText(data.getStringExtra("FROM_NAME"));
        editTo.setText(data.getStringExtra("TO_NAME"));

        editFrom.setText(data.getStringExtra("FROM_NAME"));
        editTo.setText(data.getStringExtra("TO_NAME"));

        contactOrigionPackage = (EditText) findViewById(R.id.editContactOrigionPackage);
        contactOrigionPackage.addTextChangedListener(new AddListenerOnTextChange(this, contactOrigionPackage));
        contactDestinationPackage = (EditText) findViewById(R.id.editContactDestinationPackage);
        contactDestinationPackage.addTextChangedListener(new AddListenerOnTextChange(this, contactDestinationPackage));
    }

    // display current time
    public void setCurrentTimeOnView() {

        tvDisplayTime = (TextView) findViewById(R.id.tvTime);
//        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        tvDisplayTime.setText(
                new StringBuilder().append(pad(hour))
                        .append(":").append(pad(minute)));

        // set current time into timepicker
//        timePicker1.setCurrentHour(hour);
//        timePicker1.setCurrentMinute(minute);
    }

    public void addListenerOnButton() {
        btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
        btnChangeTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    // set current time into textview
                    tvDisplayTime.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));

                    // set current time into timepicker
//                    timePicker1.setCurrentHour(hour);
//                    timePicker1.setCurrentMinute(minute);

                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private boolean isValidPackageDeatails(View view) {
        final String phone = contactOrigionPackage.getText().toString();
        if (contactOrigionPackage.getText().toString().trim().length() == 0) {
            contactOrigionPackage.setError("Add origion contact filed is required");
            return false;
        }
        final String phoneDestination = contactDestinationPackage.getText().toString();
        if (contactDestinationPackage.getText().toString().trim().length() == 0) {
            contactDestinationPackage.setError("Add destination contact filed is required");
            return false;
        }
        return true;
    }

    public void continueBtn(View view) {
        if(isValidPackageDeatails(view)){
            //Intent i = new Intent(LoginUserActivity.this, UserCurrentLocation.class);
            //startActivity(i);
        }
    }
}
