package app.pickage.com.pickage.UserActivities;

import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.pickage.com.pickage.AppConst;
import app.pickage.com.pickage.MessengerActivities.FindMessengerIntentService;
import app.pickage.com.pickage.MessengerActivities.Messenger;
import app.pickage.com.pickage.PackageActivities.Package;
import app.pickage.com.pickage.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FillPackageDetails extends AppCompatActivity implements View.OnClickListener{

    private TextView tvDisplayTime;
    private TimePicker timePicker1;
    //    private Button btnChangeTime;
    private EditText tvTime;
//    TextView textViewContactPhoneNum;
    // private static final int PICK_CONTACT = 1000;

    public static final int PICK_CONTACT_ORIGIN = 100;
    public static final int PICK_CONTACT_DEST = 101;

    private int hour;
    private int minute;

    static final int TIME_DIALOG_ID = 999;

    private EditText contactOrigionPackage;
    private EditText contactDestinationPackage;
    private ProgressBar progressbar;

    private DatabaseReference mDatabase;

    double latPackage;
    double longPackage;
    String fromName;
    String toName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_package_details);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setCurrentTimeOnView();
        addListenerOnButton();

        TextView editFrom = (TextView) findViewById(R.id.editFrom);
        TextView editTo = (TextView) findViewById(R.id.editTo);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        Intent data = getIntent();

        editFrom.setText(data.getStringExtra("FROM_NAME"));
        editTo.setText(data.getStringExtra("TO_NAME"));
        fromName = data.getStringExtra("FROM_NAME");
        toName = data.getStringExtra("TO_NAME");
        latPackage = data.getDoubleExtra("FROM_LAT", 0);
        longPackage = data.getDoubleExtra("FROM_LONG", 0);

        contactOrigionPackage = (EditText) findViewById(R.id.editContactOrigionPackage);
        contactOrigionPackage.addTextChangedListener(new AddListenerOnTextChange(this, contactOrigionPackage));
        contactDestinationPackage = (EditText) findViewById(R.id.editContactDestinationPackage);
        contactDestinationPackage.addTextChangedListener(new AddListenerOnTextChange(this, contactDestinationPackage));

//        textViewContactPhoneNum = (TextView) findViewById(R.id.editContactOrigionPackage);

        Button btnOrderMessanger = (Button) findViewById(R.id.btnOrderMessanger);
        btnOrderMessanger.setOnClickListener(this);
        ImageView addOriginContactNumPhone = (ImageView) findViewById(R.id.addOriginContactNumPhone);
        addOriginContactNumPhone.setOnClickListener(this);
        ImageView addDestContactNumPhone = (ImageView) findViewById(R.id.addDestContactNumPhone);
        addDestContactNumPhone.setOnClickListener(this);
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
            contactOrigionPackage.setError("Add origin contact filed is required");
            return false;
        }
        final String phoneDestination = contactDestinationPackage.getText().toString();
        if (contactDestinationPackage.getText().toString().trim().length() == 0) {
            contactDestinationPackage.setError("Add destination contact filed is required");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnOrderMessanger:
                if (isValidPackageDeatails(v)) {
                    Package p = new Package(0, longPackage, latPackage, fromName, toName, null, AppConst.myKey, "INITIAL");
                    String packageKey = savePackageOnFireBase(p);
                    intent = new Intent(FillPackageDetails.this, FindMessengerIntentService.class);
                    Intent data = getIntent();
                    intent.putExtras(data);
                    intent.putExtra("PACKAGE_KEY", packageKey);
                    startService(intent);
                    //progressbar.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.addOriginContactNumPhone:
                intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT_ORIGIN);
                break;
            case R.id.addDestContactNumPhone:
                intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT_DEST);
                break;
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

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT_ORIGIN):
                if (resultCode == Activity.RESULT_OK) {
                    contactOrigionPackage.setText(getPhoneNumber(data));
                }
                break;
            case PICK_CONTACT_DEST:
                if (resultCode == Activity.RESULT_OK) {
                    contactDestinationPackage.setText(getPhoneNumber(data));
                }
                break;
        }
    }

    private String getPhoneNumber(Intent data) {
        Uri contactData = data.getData();
        Cursor contact = getContentResolver().query(contactData, null, null, null, null);
        String cNumber = null;
        if (contact.moveToFirst()) {
            String contactNumberName = contact.getString(contact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String id = contact.getString(contact.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            String hasPhone = contact.getString(contact.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equalsIgnoreCase("1")) {
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                        null, null);
                phones.moveToFirst();
                cNumber = phones.getString(phones.getColumnIndex("data1"));
                System.out.println("number is: "+cNumber);
            }
        }
        return cNumber;
    }

    private String savePackageOnFireBase(Package pPackage) {
        String packageKey = mDatabase.child("packages").push().getKey();
        mDatabase.child("packages").child(packageKey).setValue(pPackage);
        return packageKey;
    }
}

