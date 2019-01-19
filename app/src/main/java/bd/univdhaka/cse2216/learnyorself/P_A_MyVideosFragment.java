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

import java.util.ArrayList;


public class P_A_MyVideosFragment extends Fragment {

    private RecyclerView recyclerView;
    private TimelineViewAdapter adapter;
    private ProgressBar progressBar;
    private Context context;
    private String userId;
    private ArrayList<timeline_object> vedios=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            View view = inflater.inflate(R.layout.fragment_p__a__my_videos, container, false);
            recyclerView = view.findViewById(R.id.recyclerView);
            context = getActivity();
            adapter = new TimelineViewAdapter(vedios, context);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            progressBar=view.findViewById(R.id.progressbar);
            userId = getActivity().getIntent().getStringExtra("userId");
            fetchData();
            return view;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

    private void fetchData() {
        DatabaseReference videoTable= FirebaseDatabase.getInstance().getReference("vedios");
        videoTable.orderByChild("owner").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if(dataSnapshot.getChildrenCount()==0){progressBar.setVisibility(View.GONE);}
                    for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        final timeline_object post = new timeline_object();
//                                System.out.println("here5");
//
                        post.setDate(dataSnapshot1.child("uploadDate").getValue().toString());
                        // contentImages.add(dataSnapshot1.child("url").getValue().toString());
                        post.setContentImage(dataSnapshot1.child("url").getValue().toString());
                        post.setPostId(dataSnapshot1.getKey());
                        post.setPath("vedios/" + dataSnapshot1.getKey());
                        String ownerId = dataSnapshot1.child("owner").getValue().toString();
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
                        database.child(ownerId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    String name = dataSnapshot.child("name").getValue().toString();
                                    // owners.add(name);
                                    post.setOwnerName(name);
                                    //    descriptions.add("#"+dataSnapshot1.child("tag").getValue().toString()+": \n"+dataSnapshot1.child("title").getValue().toString());
                                    post.setText("#" + dataSnapshot1.child("tag").getValue().toString() + ": \n" + dataSnapshot1.child("title").getValue().toString());
                                    String profilePic = dataSnapshot.child("profilePicUrl").getValue().toString();
                                    // profilePics.add(profilePic);
                                    post.setProfilePic(profilePic);
                                    //  postTypes.add(spinner.getSelectedItem().toString());
                                    post.setPostType("VIDEO");
                                    progressBar.setVisibility(View.GONE);
                                    vedios.add(post);
                                    adapter.notifyDataSetChanged();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
