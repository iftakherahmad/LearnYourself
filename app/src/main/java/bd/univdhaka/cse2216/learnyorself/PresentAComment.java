//package bd.univdhaka.cse2216.learnyorself;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.StringTokenizer;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class PresentAComment extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_present_acomment);
//    }
//}

package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.provider.ContactsContract;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.io.IOException;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Scanner;
        import java.util.StringTokenizer;

        import de.hdodenhof.circleimageview.CircleImageView;

public class PresentAComment extends Activity {
 ;
    private LinearLayout container;
    private Button updateButton;
    private DatabaseReference database;
    private CircleImageView profilePic;
    private TextView ownerNameInput;
    private TextView postingDateInput;
    private Context context;
    private ArrayList<TextView> dynamicTextViews=new ArrayList<>();
    private Scanner scanner;
    private Scanner scanner1;
    private String commentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("start..............");
        setContentView(R.layout.activity_present_acomment);
        try {
            System.out.println("1.....");
            profilePic=findViewById(R.id.profilepic);
            ownerNameInput=findViewById(R.id.owner);
            System.out.println("3.....");
            postingDateInput=findViewById(R.id.postingdate);
            //titleInput=findViewById(R.id.title);
            context=this;
            // tagInput=findViewById(R.id.tag);
            container=findViewById(R.id.container);
            updateButton=findViewById(R.id.update);
            Intent intent=getIntent();
            System.out.println("2.....");
            String parent=intent.getStringExtra("parentId");
            String postId=intent.getStringExtra("postId");
            commentPath="comments/"+parent+"/"+postId;
            database= FirebaseDatabase.getInstance().getReference(commentPath);
            System.out.println("4.....");
            setData();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setData() {
        Intent intent=getIntent();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String  postContentSequence=dataSnapshot.child("postContentSequence").getValue().toString();
                final String owner=dataSnapshot.child("owner").getValue().toString();
//                String tag=dataSnapshot.child("tag").getValue().toString();
//                String title=dataSnapshot.child("title").getValue().toString();
                setTitle("Comment");
                String postingDate=dataSnapshot.child("time").getValue().toString();
                final String imageUrls=dataSnapshot.child("imageUrls").getValue().toString();
                final String textUrls=dataSnapshot.child("textUrl").getValue().toString();

//                tagInput.setText(tag);
//                titleInput.setText(title);
                postingDateInput.setText(postingDate);
                DatabaseReference userTable=FirebaseDatabase.getInstance().getReference("users");
                userTable.child(owner).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userName=dataSnapshot.child("name").getValue().toString();
                        String profilePicUrl=dataSnapshot.child("profilePicUrl").getValue().toString();
                        ownerNameInput.setText(userName);
                        setTitle("Anser by: "+userName);
                        Glide.with(context).asBitmap().load(profilePicUrl).into(profilePic);
                        DynamicView dynamicView=new DynamicView(context);
                        StringTokenizer tokenizer=new StringTokenizer(postContentSequence);
                        while(tokenizer.hasMoreTokens()){
                            String tok1=tokenizer.nextToken("/");
                            System.out.println("token: "+tok1);
                            if(tok1.equals("txt")){
                                TextView tv1=dynamicView.createTextView("loading..",18);
                                dynamicTextViews.add(tv1);
                                container.addView(tv1);
                                new BackgroundTask1(tv1,textUrls,context).execute();

                            }
                            if(tok1.equals("image")){
                                String nextUrl=extractNextUrl(imageUrls);
                                ImageView iv1=dynamicView.createImageView(nextUrl);
                                container.addView(iv1);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String extractNextUrl(String url){
        if(scanner1==null){
            scanner1=new Scanner(url);
        }
        int len=scanner1.nextInt();
        String url1=scanner1.next();
        return  url1;
    }




    public class BackgroundTask1 extends AsyncTask<Void,Void,Void> {
        private TextView textView;
        private String url;
        private Context context;
        private String  content="";
        public BackgroundTask1(TextView textView, String url, Context context) {
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

}



