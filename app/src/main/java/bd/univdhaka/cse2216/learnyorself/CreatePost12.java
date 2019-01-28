package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

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
    private ArrayList<Uri> uris=new ArrayList<>();
    private StorageReference storage;
    private String postId;
    private DatabaseReference database;
    private FirebaseAuth authenticator;
    private String textFileUrl;
    private int uploadedImageCount;
    private Scanner scanner;
    private String imageUrls="";
    private Scanner scanner1;
    private ArrayList<TextView> dynamicTextViews=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_post12);
            setTitle("Ask Something");
            storage = FirebaseStorage.getInstance().getReference();
            database = FirebaseDatabase.getInstance().getReference();
            context = this;
            progressBar = findViewById(R.id.progressbar);
            authenticator = FirebaseAuth.getInstance();
            editTexts = new ArrayList<>();
            flag = 0;
            uris = new ArrayList<>();
            selectImageButton = findViewById(R.id.insertimage);
            addLinkButton = findViewById(R.id.insertlink);
            postButton = findViewById(R.id.post);
            titleView = findViewById(R.id.title);
            tagView = findViewById(R.id.tag);
            container = findViewById(R.id.container);
            txt1 = findViewById(R.id.txt1);
            editTexts.add(txt1);
            postContentSequence = "txt";
            postId = null;
            postId = getIntent().getStringExtra("postId");
            if (postId != null) {
                txt1.setVisibility(View.GONE);
                setData();
            }
            dynamicView = new DynamicView(this);
            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = titleView.getText().toString();
                    String tag = tagView.getText().toString();
                    if (title.length() < 3 || title.length() > 20 || tag.length() < 3 || tag.length() > 20) {
                        Toast.makeText(context, "Title and tag should have at least 3 and at most 20 characters.", Toast.LENGTH_SHORT).show();
                    } else if (flag == 1) {
                        Toast.makeText(context, "Just posted or posting.", Toast.LENGTH_SHORT).show();
                    } else {
                        flag = 1;
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
        }catch (Exception e){
            e.printStackTrace();
        }
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

            for(int i=0;i<uris.size();i++){
                imageUrls+=" "+uris.get(i).toString().length()+" "+uris.get(i).toString();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String time=formatter.format(date);
            String owner=authenticator.getCurrentUser().getUid();
            try {
                final String title = titleView.getText().toString();
                final String tag = tagView.getText().toString();
                String postType=getIntent().getStringExtra("postType");
                System.out.println("post Type:"+postType);
                if(postType==null)postType="questions";/// if activity launched from editor.
                final DatabaseReference postTable = database.child(postType);
                String postId = postTable.push().getKey();
                String title_tag=title+"_"+tag;
                final Post post = new Post(postContentSequence, title, tag, time, textFileUrl, imageUrls, owner, 0 , 0 ," "+new StringBuilder(postId).reverse().toString()+" " +
                        "",title_tag,0);
                if(CreatePost12.this.postId!=null){
                    Map updates=new HashMap<>();
                    updates.put("questions/"+CreatePost12.this.postId+"/title",title);
                    updates.put("questions/"+CreatePost12.this.postId+"/tag",tag);
                    updates.put("questions/"+CreatePost12.this.postId+"/title_tag",title+"_"+tag);
                    updates.put("questions/"+CreatePost12.this.postId+"/textUrl",textFileUrl);
                    updates.put("questions/"+CreatePost12.this.postId+"/postContentSequence",postContentSequence);
                    updates.put("questions/"+CreatePost12.this.postId+"/imageUrls",imageUrls);

                    progressBar.setVisibility(View.GONE);
                   FirebaseDatabase.getInstance().getReference().updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context,"Update successfull.",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }else {
                                Toast.makeText(context,"Update failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
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
                e.printStackTrace();
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



    public class BackgroundTask1 extends AsyncTask<Void,Void,Void> {
        private EditText textView;
        private String url;
        private Context context;
        private String  content="";
        public BackgroundTask1(EditText textView, String url, Context context) {
            this.textView = textView;
            this.url = url;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView.setText(content);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(scanner==null){
                try {
                    URL url1=new URL(url);
                    scanner=new Scanner(url1.openStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            while (scanner.hasNext()){
                String str=scanner.next();
                if(str.equals("<sPlIt.1>"))break;
                else content+=" "+str;
            }
            return null;
        }
    }



    private void setData() {
        editTexts.clear();
        try {
            Intent intent = getIntent();
            String postId = intent.getStringExtra("postId");
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("questions");
            databaseReference.child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        final String postContentSequence1 = dataSnapshot.child("postContentSequence").getValue().toString();
                        postContentSequence=postContentSequence1;
                        System.out.println(postContentSequence);

                        final String owner = dataSnapshot.child("owner").getValue().toString();
                        String tag = dataSnapshot.child("tag").getValue().toString();
                        String title = dataSnapshot.child("title").getValue().toString();
                        setTitle(title);
                        String postingDate = dataSnapshot.child("time").getValue().toString();
                        final String imageUrls = dataSnapshot.child("imageUrls").getValue().toString();
                        CreatePost12.this.imageUrls=imageUrls;
                        final String textUrls = dataSnapshot.child("textUrl").getValue().toString();
                        tagView.setText(tag);
                        titleView.setText(title);
                        DatabaseReference userTable = FirebaseDatabase.getInstance().getReference("users");
                        userTable.child(owner).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    String userName = dataSnapshot.child("name").getValue().toString();
                                    String profilePicUrl = dataSnapshot.child("profilePicUrl").getValue().toString();
                                    //  ownerNameInput.setText(userName);
                                    // Glide.with(context).asBitmap().load(profilePicUrl).into(profilePic);
                                    DynamicView dynamicView = new DynamicView(context);
                                    StringTokenizer tokenizer = new StringTokenizer(postContentSequence);
                                    while (tokenizer.hasMoreTokens()) {
                                        String tok1 = tokenizer.nextToken("/");
                                        System.out.println("token: " + tok1);
                                        if (tok1.equals("txt")) {
                                            EditText tv1 = dynamicView.createEditText();
                                            tv1.setTextSize(18);
                                            tv1.setText("loading...");
                                            // EditText tv2=dynamicView.createEditText();
                                            dynamicTextViews.add(tv1);
                                            container.addView(tv1);
                                            editTexts.add(tv1);
                                            new CreatePost12.BackgroundTask1(tv1, textUrls, context).execute();

                                        }
                                        if (tok1.equals("image")) {
                                            String nextUrl = extractNextUrl(imageUrls);
                                            ImageView iv1 = dynamicView.createImageView(nextUrl);
                                            container.addView(iv1);
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String extractNextUrl(String url){
        if(scanner1==null){
            scanner1=new Scanner(url);
        }
        int len=scanner1.nextInt();
        String url1=scanner1.next();
        return  url1;
    }

}

