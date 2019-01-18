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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class P_A_FriendsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private String userId;
    private ArrayList<User> users=new ArrayList<>();
    private Context context;
    DatabaseReference database;
    private FriendsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_p__a__friends, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressbar);
        userId= FirebaseAuth.getInstance().getUid();
        database= FirebaseDatabase.getInstance().getReference("friends/"+userId);
        context =getActivity();
        adapter=new FriendsAdapter(users,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        fetchData();
        return  view;
    }

    private void fetchData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    final String friendId=dataSnapshot1.getKey();
                    DatabaseReference userTable=FirebaseDatabase.getInstance().getReference("users/"+friendId);
                    userTable.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name=dataSnapshot.child("name").getValue().toString();
                            String profilePic=dataSnapshot.child("profilePicUrl").getValue().toString();
                            User user=new User(name,"","",profilePic,"","",friendId,0,0,0);
                            users.add(user);
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

}
