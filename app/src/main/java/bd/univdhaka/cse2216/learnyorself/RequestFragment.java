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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RequestFragment extends Fragment {

//    private ArrayList<String> names=new ArrayList<>();
//    private ArrayList<String> images=new ArrayList<>();
    private ArrayList<User> users=new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseReference database;
    private recyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_request, container, false);
        try{
        recyclerView=view.findViewById(R.id.recyclerView);
        database= FirebaseDatabase.getInstance().getReference("requests/"+ FirebaseAuth.getInstance().getUid());
        adapter=new recyclerViewAdapter(users,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initImageBitmap();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }




    private  void initImageBitmap(){
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        DatabaseReference userTable=FirebaseDatabase.getInstance().getReference("users/"+dataSnapshot1.getKey());
                        userTable.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String name=dataSnapshot.child("name").getValue().toString();
                                String profilePic=dataSnapshot.child("profilePicUrl").getValue().toString();
                                String userId=dataSnapshot1.getKey();
                                User user = new User(name, "", "", profilePic, "", "", userId, 0, 0, 0);
                                users.add(user);
                                adapter.notifyDataSetChanged();
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
