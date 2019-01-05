package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchActivity extends Activity {
    private RecyclerView recyclerView;
    private ArrayList<String> profilePics=new ArrayList<>();
    private ArrayList<String> owners=new ArrayList<>();
    private ArrayList<String> uploadDates=new ArrayList<>();
    private ArrayList<String> descriptions=new ArrayList<>();
    private ArrayList<String> contentImages=new ArrayList<>();
    private ArrayList<String> postTypes=new ArrayList<>();
    private ArrayList<String> titleTags=new ArrayList<>();
    private ArrayList<String> postIds=new ArrayList<>();
    private TimelineViewAdapter adapter;
    private Spinner spinner;
    private ProgressBar progressBar;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context=this;
        setTitle("Search");
        recyclerView=findViewById(R.id.recyclerView);
        progressBar =findViewById(R.id.progressbar);
        adapter=new TimelineViewAdapter(postIds,postTypes,context,profilePics,owners,uploadDates,contentImages,descriptions);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        spinner=findViewById(R.id.contenttype);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search_activity,menu);
        MenuItem menuItem=menu.findItem(R.id.searchbar);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                progressBar.setVisibility(View.VISIBLE);

                updateRecyclerView(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void updateRecyclerView(String query) {
        profilePics.clear();
        owners.clear();
        descriptions.clear();
        uploadDates.clear();
        contentImages.clear();
        postTypes.clear();
        titleTags.clear();
        postIds.clear();

        String type=spinner.getSelectedItem().toString();
//        Log.d("print in logcat","second");
//        System.out.println("here2");
        if(type.equals("VIDEO")){///if video is searched........................................................................
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("vedios");
//            Log.d("print in logcat","third");
//            System.out.println("here3");
            databaseReference
                    .orderByChild("title")
                    .startAt("%"+query+"%")
                    .endAt(query+"\uf8ff")
                    .limitToFirst(10)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Toast.makeText(context,"first",Toast.LENGTH_SHORT).show();
//                            Log.d("print in logcat","fourth");
//                            System.out.println("here4");
                            for(final DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                                System.out.println("here5");
//                                System.out.println("here6");
                                uploadDates.add(dataSnapshot1.child("uploadDate").getValue().toString());
                                contentImages.add(dataSnapshot1.child("url").getValue().toString());

                                String ownerId=dataSnapshot1.child("owner").getValue().toString();
                                DatabaseReference database=FirebaseDatabase.getInstance().getReference("users");
                                database.child(ownerId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String name=dataSnapshot.child("name").getValue().toString();
                                        owners.add(name);
                                        descriptions.add("#"+dataSnapshot1.child("tag").getValue().toString()+": \n"+dataSnapshot1.child("title").getValue().toString());
                                        String profilePic=dataSnapshot.child("profilePicUrl").getValue().toString();
                                        profilePics.add(profilePic);
                                        postTypes.add(spinner.getSelectedItem().toString());
                                        progressBar.setVisibility(View.GONE);
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
        if(type.equals("QUESTION")){/// if question is searched...............................................................
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("questions");
            System.out.println("first");
            databaseReference
                    .orderByChild("title")
                    .startAt("%${+query}%")
                    .endAt(query+"\uf8ff")
                    .limitToFirst(20)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Toast.makeText(context,"first",Toast.LENGTH_SHORT).show();
                            System.out.println("second");
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                System.out.println("third");
                                String imageUrls=dataSnapshot1.child("imageUrls").getValue().toString();
                                String firstUrl="no image";
                                if(imageUrls.length()>5)firstUrl=extractFirstUrl(imageUrls);
                                System.out.println("fourth");
                                contentImages.add(firstUrl);
                                uploadDates.add(dataSnapshot1.child("time").getValue().toString());

                                final String txtUrl=dataSnapshot1.child("textUrl").getValue().toString();
                                System.out.println("fifth");
                                final String titleTag="#"+dataSnapshot1.child("tag").getValue().toString()+": \n"+dataSnapshot1.child("title").getValue().toString()+"\n";
                                String ownerId=dataSnapshot1.child("owner").getValue().toString();
                                String pid=dataSnapshot1.getKey();
                                postIds.add(pid);
                                System.out.println("six");
                                DatabaseReference database=FirebaseDatabase.getInstance().getReference("users");
                                database.child(ownerId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String name=dataSnapshot.child("name").getValue().toString();
                                        System.out.println("seven");
                                        owners.add(name);
                                        String profilePic=dataSnapshot.child("profilePicUrl").getValue().toString();
                                        profilePics.add(profilePic);
                                        System.out.println("eight");
                                        new BackGroundTask(descriptions,txtUrl,titleTag,recyclerView,adapter,context).execute();
                                        postTypes.add(spinner.getSelectedItem().toString());
                                        progressBar.setVisibility(View.GONE);
                                        System.out.println("ten");
                                        adapter.notifyDataSetChanged();
                                        System.out.println("nine");
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    private String extractFirstUrl(String imageUrls) {
        Scanner scanner=new Scanner(imageUrls);
        int urlLength=scanner.nextInt();
        String remaining=scanner.nextLine();
        String url1=remaining.substring(1,urlLength);
        return url1;
    }
}
