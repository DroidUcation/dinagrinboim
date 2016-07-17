package app.pickage.com.pickage.useractivities;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.squareup.picasso.Picasso;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import app.pickage.com.pickage.R;

public class UploadUserImg extends AppCompatActivity {

    private static final int READ_STORAGE_PERMISSIONS_CODE = 12;
    EditText phoneEditText;
    ImageView viewImage;
    String keyUser;

    private DatabaseReference mDatabase;
    private StorageReference storageRef;
    private Uri tempFileuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_user_img);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://packme-ea467.appspot.com/");// + this.getString(R.string.google_storage_bucket));

        phoneEditText = (EditText) findViewById(R.id.input_phone);
        viewImage = (ImageView) findViewById(R.id.viewImage);
        Intent intent = getIntent();
        keyUser = intent.getStringExtra("USER_KEY");
    }

    public void ContinueBTN(View view) {
        mDatabase.child("users").child(keyUser).child("userPhone").setValue(phoneEditText.getText().toString());
        Intent i = new Intent(UploadUserImg.this, UserCreditCardDetails.class);
        i.putExtra("USER_KEY", keyUser);
        startActivity(i);
    }


    public void grabImage(ImageView imageView) {
        this.getContentResolver().notifyChange(tempFileuri, null);
        ContentResolver cr = this.getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, tempFileuri);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "Failed to load", e);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (tempFileuri == null) {
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    tempFileuri = Uri.fromFile(f);
                }
                Picasso.with(this).load(tempFileuri).into(viewImage);
//                this.grabImage(viewImage);
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                tempFileuri = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath, options));
                viewImage.setImageBitmap(thumbnail);
            }
            uploadImgToFireBase();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImgToFireBase() {
//        StorageReference userRef = storageRef.child("images/users/" + keyUser + ".jpg");
//        viewImage.setDrawingCacheEnabled(true);
//        viewImage.buildDrawingCache();
//        Bitmap bitmap = viewImage.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
//        byte[] data = baos.toByteArray();
//        UploadTask uploadTask = userRef.putBytes(data);

        Uri file = tempFileuri;
        StorageReference userRef = storageRef.child("images/users/" + keyUser + ".jpg");
        UploadTask uploadTask = userRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    public void selectImages(View view) {

        if (isStoragePermissionGranted()) {

            //Creating the instance of PopupMenu
            PopupMenu popup = new PopupMenu(this, viewImage);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    if (item.getTitle().equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        tempFileuri = Uri.fromFile(f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileuri);
                        startActivityForResult(intent, 1);
                    } else if (item.getTitle().equals("Choose from Gallery")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                    return true;
                }
            });

            popup.show();//showing popup menu
        }
    }

    public boolean isStoragePermissionGranted() {
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_STORAGE_PERMISSIONS_CODE);

                // READ_STORAGE_PERMISSIONS_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_STORAGE_PERMISSIONS_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    selectImages(null);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



}
