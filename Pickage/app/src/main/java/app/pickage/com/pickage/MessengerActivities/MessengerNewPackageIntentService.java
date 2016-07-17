package app.pickage.com.pickage.messengeractivities;

import android.app.IntentService;
import android.content.Intent;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.pickage.com.pickage.AppConst;
import app.pickage.com.pickage.packageactivities.Package;

/**
 * Created by Din&Yeudit 12/07/2016.
 */
public class MessengerNewPackageIntentService extends IntentService {

    private DatabaseReference mDatabase;

    public MessengerNewPackageIntentService() {
        super("MessengerNewPackageIntentService");
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mDatabase.child("packages").addChildEventListener(
                new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Package pPackage = dataSnapshot.getValue(Package.class);
                        if (pPackage.getpMessengerID() != null && pPackage.getpMessengerID().equals(AppConst.myKey)
                                && pPackage.getPackageStatus().equals("INITIAL")) {
                            Intent getUserPackageIntent = new Intent(getBaseContext(), GetUserPackage.class);
                            getUserPackageIntent.putExtra("PACKAGE_KEY", dataSnapshot.getKey());
                            getUserPackageIntent.putExtra("FROM_NAME", pPackage.getOriginPackage());
                            getUserPackageIntent.putExtra("FROM_CONTACT", pPackage.getOriginContact());
                            getUserPackageIntent.putExtra("FROM_LAT", pPackage.getPkgOriginLat());
                            getUserPackageIntent.putExtra("FROM_LONG", pPackage.getPkgOriginLong());
                            getUserPackageIntent.putExtra("TO_NAME", pPackage.getDestinationPackage());
                            getUserPackageIntent.putExtra("TO_CONTACT", pPackage.getDestinationContact());
                            getUserPackageIntent.putExtra("USER_KEY", pPackage.getpUserID());
                            getUserPackageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplication().startActivity(getUserPackageIntent);
                        }
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}