package app.pickage.com.pickage.UserActivities;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.pickage.com.pickage.R;

import java.text.DecimalFormat;
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

import com.google.android.gms.maps.model.LatLng;


import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class FillPackageDetails extends AppCompatActivity {

    private TextView tvDisplayTime;
    private TimePicker timePicker1;
    //    private Button btnChangeTime;
    private EditText tvTime;
    TextView textViewContactPhoneNum;
    // private static final int PICK_CONTACT = 1000;

    public static final int PICK_CONTACT = 100;

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

        contactOrigionPackage = (EditText) findViewById(R.id.editContactOrigionPackage);
        contactOrigionPackage.addTextChangedListener(new AddListenerOnTextChange(this, contactOrigionPackage));
        contactDestinationPackage = (EditText) findViewById(R.id.editContactDestinationPackage);
        contactDestinationPackage.addTextChangedListener(new AddListenerOnTextChange(this, contactDestinationPackage));

        textViewContactPhoneNum = (TextView) findViewById(R.id.editContactOrigionPackage);
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
        tvTime = (EditText) findViewById(R.id.tvTime);
        tvTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);

            }
        });
//        btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
//        btnChangeTime.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                showDialog(TIME_DIALOG_ID);
//
//            }
//        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute, false);
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

    private void orderMessengerBtn(View view) {
        if (isValidPackageDeatails(view)) {
            Intent i = new Intent(FillPackageDetails.this, FindingMessenger.class);
            startActivity(i);
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }


    //Todo when button is clicked
    public void pickAContactNumber(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor phone = getContentResolver().query(contactData, null, null, null, null);
                    if (phone.moveToFirst()) {
                        String contactNumberName = phone.getString(phone.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        // Todo something when contact number selected
                        textViewContactPhoneNum.setText("Name: " + contactNumberName);
                    }
                }
                break;
        }
    }
}
