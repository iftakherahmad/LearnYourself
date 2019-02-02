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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{
    private ArrayList<User> users;
    private Context context;
    private String  owner;

    public FriendsAdapter(ArrayList<User> users, Context context,String owner) {
        this.users = users;
        this.context = context;
        this.owner=owner;
    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.my_friend_sample,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.userName.setText(users.get(i).getName());
        GlideApp.with(context).asBitmap().load(users.get(i).getProfilePicUrl()).into(viewHolder.profilePic);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null && FirebaseAuth.getInstance().getUid().equals(owner))viewHolder.unfriend.setText("UNFRIEND");
        else viewHolder.unfriend.setVisibility(View.GONE);
        viewHolder.unfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.unfriend.getText().equals("UN FRIEND")) {
                    FirebaseDatabase.getInstance().getReference("friends/" + FirebaseAuth.getInstance().getUid() + "/" + users.get(i).getUserId()).removeValue();
                    FirebaseDatabase.getInstance().getReference("friends/" + users.get(i).getUserId() + "/" + FirebaseAuth.getInstance().getUid()).removeValue();
                }
                viewHolder.unfriend.setBackgroundColor(Color.TRANSPARENT);
                if(FirebaseAuth.getInstance().getCurrentUser()!=null && FirebaseAuth.getInstance().getUid().equals(owner))viewHolder.unfriend.setText("UNFRIENDED");
                else viewHolder.unfriend.setVisibility(View.GONE);
            }
        });
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ProfileActivity.class);
                intent.putExtra("userId",users.get(i).getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePic;
        TextView userName;
        Button unfriend;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.profilepic);
            userName=itemView.findViewById(R.id.name);
            unfriend=itemView.findViewById(R.id.unfriend);
            parentLayout=itemView.findViewById(R.id.parentlayout);
        }
    }
}
