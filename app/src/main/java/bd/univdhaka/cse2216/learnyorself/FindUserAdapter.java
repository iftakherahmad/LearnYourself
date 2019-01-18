package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindUserAdapter extends RecyclerView.Adapter<FindUserAdapter.ViewHolder>{
    private ArrayList<User> users;
    private Context context;

    public FindUserAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        try {
            view = LayoutInflater.from(context).inflate(R.layout.sample_user, viewGroup, false);
        }catch (Exception e){
            e.printStackTrace();
            view=null;
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        System.out.println("---3");
        try {
            viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ProfileActivity.class);
                    intent.putExtra("userId",users.get(i).getUserId());
                    context.startActivity(intent);
                }
            });
            viewHolder.name.setText(users.get(i).getName());
            Glide.with(context).asBitmap().load(users.get(i).getProfilePicUrl()).into(viewHolder.profilePic);
            FirebaseDatabase.getInstance().getReference("friends/"+FirebaseAuth.getInstance().getUid()).orderByKey().equalTo(users.get(i).getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override//check wheateher this person is my friend
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0)viewHolder.addfriend.setText("FRIEND");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            FirebaseDatabase.getInstance().getReference("requests/"+FirebaseAuth.getInstance().getUid()).orderByKey().equalTo(users.get(i).getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override//cheack wheather wheather this person sent requested me.
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0)viewHolder.addfriend.setText("CONFIRM ");
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            FirebaseDatabase.getInstance().getReference("sentRequests/"+FirebaseAuth.getInstance().getUid()).orderByKey().equalTo(users.get(i).getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override//cheack wheather i already has sent request
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0)viewHolder.addfriend.setText("CANCEL");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            viewHolder.addfriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewHolder.addfriend.getText().equals("ADD FRIEND")){
                        DatabaseReference dropBox= FirebaseDatabase.getInstance().getReference("requests/"+users.get(i).getUserId());
                        dropBox.child(FirebaseAuth.getInstance().getUid()).setValue("true");
                        viewHolder.addfriend.setText("Request sent");
                       /// viewHolder.addfriend.setBackgroundColor(Color.WHITE);
                        viewHolder.addfriend.setTextColor(Color.BLACK);
                        FirebaseDatabase.getInstance().getReference("sentRequests/"+FirebaseAuth.getInstance().getUid()).child(users.get(i).getUserId()).setValue("true");
                    }
                    else if(viewHolder.addfriend.getText().equals("CONFIRM")){

                        FirebaseDatabase.getInstance().getReference("friends/"+ FirebaseAuth.getInstance().getUid()).child(users.get(i).getUserId()).setValue("true");//adding to my friend list
                        FirebaseDatabase.getInstance().getReference("requests/"+FirebaseAuth.getInstance().getUid()).child(users.get(i).getUserId()).removeValue();// removing from hanging request
                        FirebaseDatabase.getInstance().getReference("friends/"+ users.get(i).getUserId()).child(FirebaseAuth.getInstance().getUid()).setValue("true");// adding my id to friend's friend list
                        FirebaseDatabase.getInstance().getReference("sentRequests/"+users.get(i).getUserId()).child(FirebaseAuth.getInstance().getUid()).removeValue();//removing from friend's sent request list
                        viewHolder.addfriend.setText("FRIEND");
                    }
                    else if(viewHolder.addfriend.getText().equals("CANCEL")){
                        FirebaseDatabase.getInstance().getReference("sentRequests/"+FirebaseAuth.getInstance().getUid()).child(users.get(i).getUserId()).removeValue();//removing from my sent requests
                        FirebaseDatabase.getInstance().getReference("requests/"+users.get(i).getUserId()).child(FirebaseAuth.getInstance().getUid()).removeValue();//removing receiver Box
                        viewHolder.addfriend.setText("ADD FRIEND");
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         CircleImageView profilePic;
         TextView name;
         Button addfriend;
         Button report;
         LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            System.out.println("---1");
            try {
                profilePic = itemView.findViewById(R.id.profilepic);
                name = itemView.findViewById(R.id.name);
                addfriend = itemView.findViewById(R.id.addfriend);
                report = itemView.findViewById(R.id.report);
                parentLayout=itemView.findViewById(R.id.parentlayout);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
