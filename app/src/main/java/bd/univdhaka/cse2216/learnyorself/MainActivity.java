package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends Activity {
    RecyclerView recyclerView;
    TimelineViewAdapter adapter;
    ArrayList<timeline_object> videos=new ArrayList<>();



    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            setTitle(getResources().getString(R.string.title));
            FirebaseAuth.getInstance().signOut();
            recyclerView = findViewById(R.id.recyclerView);
            adapter = new TimelineViewAdapter(videos, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            fetchTopVideos();
//        pdfView=findViewById(R.id.pdfview1);
//        pdfView.fromAsset("database.pdf").load();
            //Toast.makeText(this,getClass().getName()+" "+"kutub",Toast.LENGTH_SHORT);
        }catch (Exception e){e.printStackTrace();}

    }

    private void fetchTopVideos() {
        DatabaseReference df=FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase.getInstance().getReference("vedios").orderByChild("like").limitToFirst(20).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String owner = dataSnapshot1.child("owner").getValue().toString();
                        final String title = dataSnapshot1.child("title").getValue().toString();
                        final String tag = dataSnapshot1.child("tag").getValue().toString();
                        final String time = dataSnapshot1.child("uploadDate").getValue().toString();
                        final String url = dataSnapshot1.child("url").getValue().toString();
                        final String postId = dataSnapshot1.getKey();
                        FirebaseDatabase.getInstance().getReference("users/" + owner).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    String ownerName = dataSnapshot.child("name").getValue().toString();
                                    String profilePic = dataSnapshot.child("profilePicUrl").getValue().toString();
                                    String path = "vedios/" + postId;
                                    timeline_object video = new timeline_object("VIDEO", title, tag, "#" + tag + "\n" + title, profilePic, ownerName, time, url, postId, path);
                                    videos.add(video);
                                    adapter.notifyDataSetChanged();
                                }catch (Exception e){e.printStackTrace();}
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        //Toast.makeText(this,"home",Toast.LENGTH_SHORT).show();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.signup) {
            Toast.makeText(this, "Signup Clicked", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,SignUpActivity3.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.login) {
           // Toast.makeText(this, "Log in Clicked", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(this,LoginActivity4.class);
            startActivity(intent1);
        }
        if(item.getItemId()==R.id.search) {
          //  Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,SearchActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.about)
        {
            Intent intent =new Intent(this,AboutActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }
}
