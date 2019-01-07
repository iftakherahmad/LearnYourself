package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreatePost12 extends Activity {
    private ImageView selectImageButton;
    private int flag;
    private ImageView addLinkButton;
    private Button postButton;
    private EditText titleView;
    private EditText tagView;
    private LinearLayout container;
    private ProgressBar progressBar;
    private EditText txt1;
    private Context context;
    private String postContentSequence;
    private DynamicView dynamicView;
    private ArrayList<EditText> editTexts;
    private ArrayList<Uri> uris;
    private StorageReference storage;
    private DatabaseReference database;
    private FirebaseAuth authenticator;
    private String textFileUrl;
    private int uploadedImageCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post12);
        setTitle("Ask Something");
        storage= FirebaseStorage.getInstance().getReference();
        database= FirebaseDatabase.getInstance().getReference();
        context=this;
        progressBar=findViewById(R.id.progressbar);
        authenticator=FirebaseAuth.getInstance();
        editTexts=new ArrayList<>();
        flag=0;
        uris =new ArrayList<>();
        selectImageButton=findViewById(R.id.insertimage);
        addLinkButton=findViewById(R.id.insertlink);
        postButton=findViewById(R.id.post);
        titleView=findViewById(R.id.title);
        tagView=findViewById(R.id.tag);
        container=findViewById(R.id.container);
        txt1=findViewById(R.id.txt1);
        editTexts.add(txt1);
        postContentSequence="txt";
        dynamicView=new DynamicView(this);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleView.getText().toString();
                String tag=tagView.getText().toString();
                if(title.length()<3 || title.length()>20 ||tag.length()<3 || tag.length()>20){
                    Toast.makeText(context,"Title and tag should have at least 3 and at most 20 characters.",Toast.LENGTH_SHORT).show();
                }
                 else if(flag==1){
                    Toast.makeText(context,"Just posted or posting.",Toast.LENGTH_SHORT).show();
                }
                else {
                     flag=1;
                    progressBar.setVisibility(View.VISIBLE);
                    postContent();
                   // Toast.makeText(context,"hee",Toast.LENGTH_SHORT).show();
                }
            }
        });
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void postContent() {
        Uri textUri;
        try{
            File file=new File(context.getFilesDir(),"post.txt");
            if(!file.exists())file.createNewFile();
            BufferedWriter bufferedWriter1=new BufferedWriter(new FileWriter(file,false));
            bufferedWriter1.write("");
            bufferedWriter1.close();
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file,true));
            bufferedWriter.flush();
            for(int i=0;i<editTexts.size();i++){
                String content=editTexts.get(i).getText().toString();
                int contentLength=content.length();
                bufferedWriter.write(content);
                bufferedWriter.newLine();
                bufferedWriter.write(" <sPlIt.1> ");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            textUri=Uri.fromFile(file);
            uploadFiles(textUri);

        }catch (Exception e){
            Toast.makeText(CreatePost12.this,"IO Exception occoured",Toast.LENGTH_SHORT).show();
        }


    }

    private void uploadFiles(final Uri textUri) {
        storage.child("posts").child("texts").child(System.currentTimeMillis()+".txt").putFile(textUri).addOnCompleteListener(
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                    }
                }
        ).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                textFileUrl=taskSnapshot.getDownloadUrl().toString();
                uploadedImageCount=0;
              //  Toast.makeText(context,"hee",Toast.LENGTH_SHORT).show();
                uploadImages();

            }
        });
    }

    private void uploadImages() {
        if(uploadedImageCount==uris.size()){// base case of recursion
            String imageUrls="";
            for(int i=0;i<uris.size();i++){
                imageUrls+=" "+uris.get(i).toString().length()+" "+uris.get(i).toString();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String time=formatter.format(date);
            String owner=authenticator.getCurrentUser().getUid();
            try {
                String title = titleView.getText().toString();
                String tag = tagView.getText().toString();
                Post post = new Post(postContentSequence, title, tag, time, textFileUrl, imageUrls, owner, 0 + "", 0 + "");
                String postType=getIntent().getStringExtra("postType");
                System.out.println("post Type:"+postType);
                DatabaseReference postTable = database.child(postType);
                String postId = postTable.push().getKey();
                postTable.child(postId).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CreatePost12.this, "Posted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                           flag=0;
                        }
                    }
                });
            }catch (Exception e){
                Toast.makeText(context,"null pointer exception.",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        storage.child("posts").child("images").child(System.currentTimeMillis()+"").putFile(uris.get(uploadedImageCount)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                uploadedImageCount++;
                uploadImages();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uris.set(uploadedImageCount,taskSnapshot.getDownloadUrl());
            }
        });
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==79 && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            displaySelectedImage(uri);

        }
    }

    private void displaySelectedImage(Uri uri) {
        ImageView imageView=dynamicView.createImageView(uri.toString());
        container.addView(imageView);
        postContentSequence+="/image/txt";
        uris.add(uri);
        EditText editText=dynamicView.createEditText();
        container.addView(editText);
        editTexts.add(editText);
    }

    private void selectImage() {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/jpeg");
        startActivityForResult(intent,79);
    }
}

