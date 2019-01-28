package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home_Fragment extends Fragment {
//     PDFView pdfView;
    RecyclerView recyclerView;
    TimelineViewAdapter adapter;
    ArrayList<timeline_object> videos=new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home,container,false);
                        try {
                            recyclerView = view.findViewById(R.id.recyclerView);
                            adapter = new TimelineViewAdapter(videos, getActivity());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            fetchTopVideos();
                //        pdfView=findViewById(R.id.pdfview1);
                //        pdfView.fromAsset("database.pdf").load();
                            //Toast.makeText(this,getClass().getName()+" "+"kutub",Toast.LENGTH_SHORT);
                        }catch (Exception e){e.printStackTrace();}

        return view;

    }

    private void fetchTopVideos() {
        DatabaseReference df= FirebaseDatabase.getInstance().getReference();
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

//    private void pdfViewer(View view){
//        pdfView=view.findViewById(R.id.pdfview);
//        pdfView.fromAsset("database.pdf").load();
//    }


}
