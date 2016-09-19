package app.pickage.com.pickage.common;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;

import app.pickage.com.pickage.R;

/**
 * Created by משפחת אוביץ on 14/07/2016.
 */
public class UploadImages {

    private static String logTag = UploadImages.class.getName();

    public interface OnUploadCompletedListener{
        void onUrlReceived(Uri uri);
    }

    public static void uploadFile(Context context, Uri fileUri , final OnUploadCompletedListener listener, String fileCategory){

//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReferenceFromUrl("gs://" + context.getString(R.string.google_storage_bucket));
//
//        // Create a child reference imagesRef now points to "images"
//        StorageReference imagesRef = storageRef.child("images");
//        StorageReference itemRef = storageRef.child("images/"+fileCategory+"s/"+fileCategory+"_img"+String.valueOf(System.currentTimeMillis())+fileUri.getLastPathSegment());
//        UploadTask uploadTask = itemRef.putFile(fileUri);
//
//        // Register observers to listen for when the download is done or if it fails
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.e(logTag,"Failed to upload file to firebase storage");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                final Uri downloadUri = taskSnapshot.getDownloadUrl();
//                Log.i(logTag, "Upload file to firebase storage successfully");
//
//                listener.onUrlReceived(downloadUri);
//            }
//        });
    }

}
