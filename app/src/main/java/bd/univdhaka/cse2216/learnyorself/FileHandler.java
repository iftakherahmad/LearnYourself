package bd.univdhaka.cse2216.learnyorself;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FileHandler {
    StorageReference storage;
    String dowloadUrl;
    ProgressDialog progressDialog;
    Context context;

    FileHandler(Context context){
        this.context=context;
        storage=FirebaseStorage.getInstance().getReference();
        progressDialog=new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.show();
        progressDialog.setProgress(0);
    }



    public void uploadPhoto(Uri imageUri) {
        storage.child("Profile pics").child(System.currentTimeMillis()+"").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dowloadUrl=taskSnapshot.getDownloadUrl().toString();
                Toast.makeText(context,"url:"+dowloadUrl,Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int progress= ((100*(int) (taskSnapshot.getBytesTransferred()) /(int) taskSnapshot.getTotalByteCount()));

                progressDialog.setProgress(progress);
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                progressDialog.hide();
                Toast.makeText(context,"File uploaded successfully.",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  String getDowloadUrl(){
        return dowloadUrl;
    }

}
