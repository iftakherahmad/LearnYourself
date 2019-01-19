package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;


public class P_A_MyQuestionsFrgment extends Fragment {
    private RecyclerView recyclerView;
    private TimelineViewAdapter adapter;
    private ProgressBar progressBar;
    private String userId;
    private ArrayList<timeline_object> questions=new ArrayList<>();
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_p__a__my_questions_frgment, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        context=getActivity();
        adapter=new TimelineViewAdapter(questions,context);
        progressBar=view.findViewById(R.id.progressbar);
        userId=getActivity().getIntent().getStringExtra("userId");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        fetchData();
        return view;
    }

    private void fetchData() {
        DatabaseReference questionTable=FirebaseDatabase.getInstance().getReference("questions");
        questionTable.orderByChild("owner").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    System.out.println("third");
                    String imageUrls=dataSnapshot1.child("imageUrls").getValue().toString();
                    String firstUrl="no image";
                    if(imageUrls.length()>5)firstUrl=extractFirstUrl(imageUrls);
                    final timeline_object post=new timeline_object();
                    System.out.println("fourth");
                    //contentImages.add(firstUrl);
                    post.setContentImage(firstUrl);
                    post.setPath("questions/"+dataSnapshot1.getKey());//data changed ++++++++++++++++++++++++++++
                    //uploadDates.add(dataSnapshot1.child("time").getValue().toString());
                    post.setDate(dataSnapshot1.child("time").getValue().toString());
                    final String txtUrl=dataSnapshot1.child("textUrl").getValue().toString();
                    System.out.println("fifth");
                    final String titleTag="#"+dataSnapshot1.child("tag").getValue().toString()+": \n"+dataSnapshot1.child("title").getValue().toString()+"\n";
                    String ownerId=dataSnapshot1.child("owner").getValue().toString();
                    String pid=dataSnapshot1.getKey();
                    // postIds.add(pid);
                    post.setPostId(pid);
                    System.out.println("six");
                    DatabaseReference database=FirebaseDatabase.getInstance().getReference("users");
                    database.child(ownerId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name=dataSnapshot.child("name").getValue().toString();
                            System.out.println("seven");
                            //owners.add(name);
                            post.setOwnerName(name);
                            String profilePic=dataSnapshot.child("profilePicUrl").getValue().toString();
                            //profilePics.add(profilePic);
                            post.setProfilePic(profilePic);
                            System.out.println("eight");
                            new BackGroundTask(questions,0,post,txtUrl,titleTag,recyclerView,adapter,context).execute();//This should be ..................................................
                            // postTypes.add(spinner.getSelectedItem().toString());
                            post.setPostType("QUESTION");
                            progressBar.setVisibility(View.GONE);
                            System.out.println("ten");

                            //adapter.notifyDataSetChanged();
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

    private String extractFirstUrl(String imageUrls) {
        Scanner scanner=new Scanner(imageUrls);
        int urlLength=scanner.nextInt();
        String remaining=scanner.nextLine();
        String url1=remaining.substring(1,urlLength);
        return url1;
    }

}
