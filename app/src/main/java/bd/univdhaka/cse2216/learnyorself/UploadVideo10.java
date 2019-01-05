package bd.univdhaka.cse2216.learnyorself;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class UploadVideo10 extends Activity {
    private EditText titleInput;
    private EditText tagInput;
    private VideoView vedioView;
    private Button uploadButton;
    private Button selectButton;
    private Uri uri;
    private StorageReference storage;
    private TextView fileNameView;
    private ProgressBar progressBar;
    private FirebaseAuth authenticator;
    private Uri cache;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video10);
        titleInput=findViewById(R.id.title);
        storage=FirebaseStorage.getInstance().getReference();
        tagInput=findViewById(R.id.tag);
        authenticator=FirebaseAuth.getInstance();
        setTitle("UPLOAD VEDIO");
        vedioView=findViewById(R.id.vedioview);
        progressBar=findViewById(R.id.progressbar);
        uploadButton=findViewById(R.id.uploadvideo);
        selectButton=findViewById(R.id.selectvideo);
        fileNameView=findViewById(R.id.filename);
        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(vedioView);
        vedioView.setMediaController(mediaController);
        database=FirebaseDatabase.getInstance().getReference("vedios");
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(UploadVideo10.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectVedio();
                }
                else {
                    ActivityCompat.requestPermissions(UploadVideo10.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
                }
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri==null){
                    Toast.makeText(UploadVideo10.this,"Please select a valid vedio formet file.",Toast.LENGTH_SHORT).show();
                }
                else if(uri.equals(cache)){
                    Toast.makeText(UploadVideo10.this,"You have just uploaded this video.",Toast.LENGTH_SHORT).show();
                }
                else if(uri!=null){
                    String title=titleInput.getText().toString();
                    String tag=tagInput.getText().toString();
                    if(title.length()>=6 && tag.length()>=3 &&tag.length()<=30){
                        cache=uri;
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(0);
                        uploadVedio();
                    }
                    else{
                        Toast.makeText(UploadVideo10.this,"Title should be have at least 6 and tag should have at least 3 characters.",Toast.LENGTH_LONG).show();
                    }
                }
                else Toast.makeText(UploadVideo10.this,"Please select a valid vedio formet file.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadVedio() {
        String title=titleInput.getText().toString();
        String tag=tagInput.getText().toString();
        storage.child("vedios").child(System.currentTimeMillis()+"").putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {



            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                long transferred=  taskSnapshot.getBytesTransferred();
                long total=taskSnapshot.getTotalByteCount();
                long progress=(transferred*100)/total;
                progressBar.setProgress((int) progress);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadVideo10.this,"Something went wrong upload cancelled !",Toast.LENGTH_SHORT).show();
                cache=null;
                progressBar.setVisibility(View.GONE);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url=taskSnapshot.getDownloadUrl().toString();
                String owner=authenticator.getCurrentUser().getUid();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String time=formatter.format(date);
                String title=titleInput.getText().toString();
                String tag=tagInput.getText().toString();
                if(title.length()>=6 && tag.length()>=3){
                    Vedio vedio=new Vedio(url,title,tag,owner,time);
                                String vid=database.push().getKey();
                    database.child(vid).setValue(vedio).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(UploadVideo10.this,"Upload finished.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(UploadVideo10.this,"Title should be have at least 6 and tag should have at least 3 characters.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==10 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectVedio();
        }
        else {
            ActivityCompat.requestPermissions(UploadVideo10.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==82 && resultCode==RESULT_OK && data!=null){
            uri=data.getData();
            vedioView.setVideoURI(uri);
            fileNameView.setText("File:"+uri.getLastPathSegment());
            vedioView.start();
        }
        else {
            Toast.makeText(UploadVideo10.this,"Please select a vedio",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectVedio() {
        Intent intent=new Intent();
        intent.setType("application/mp4");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,82);
    }

}
