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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Notification extends Fragment {

    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    NotificationViewAdapter adapter;
    private String  currentUserId;
    private  DatabaseReference database;
    private ArrayList<NotificationObject> notifications=new ArrayList<>();





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        try{

            currentUserId=FirebaseAuth.getInstance().getUid();
            database=FirebaseDatabase.getInstance().getReference("notifications/"+currentUserId);
            dataSetter();
            processRecyclerView(view);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return view;

    }
    private void processRecyclerView( View view){
        recyclerView=view.findViewById(R.id.recyclerView);
        if(recyclerView==null){
            Toast.makeText(getActivity(),"recyclerView is null",Toast.LENGTH_SHORT).show();
        }
        else {
            layoutManager = new LinearLayoutManager(getActivity());
            adapter =new NotificationViewAdapter(notifications,getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
    private  void dataSetter(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    NotificationObject notification=new NotificationObject();
                    notification.setInvolvedPerson(dataSnapshot1.child("involvedPerson").getValue().toString());
                    notification.setPostType(dataSnapshot1.child("postType").getValue().toString());
                    notification.setInvolvedPost(dataSnapshot1.child("involvedPost").getValue().toString());
                    notification.setTime(dataSnapshot1.child("time").getValue().toString());
                    if(dataSnapshot1.child("message").getValue().toString().equals("new comment")){
                        notification.setMessage(" commented on your post:\n");
                    }
                    else {
                        notification.setMessage(" liked on your post:\n");
                    }
                    notifications.add(notification);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
