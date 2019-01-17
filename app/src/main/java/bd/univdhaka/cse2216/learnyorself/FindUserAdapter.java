package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            viewHolder.name.setText(users.get(i).getName());
            Glide.with(context).asBitmap().load(users.get(i).getProfilePicUrl()).into(viewHolder.profilePic);
            viewHolder.addfriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference dropBox= FirebaseDatabase.getInstance().getReference("requests/"+users.get(i).getUserId());
                    dropBox.child(FirebaseAuth.getInstance().getUid()).setValue("true");
                    viewHolder.addfriend.setText("Request sent");
                    viewHolder.addfriend.setBackgroundColor(Color.WHITE);
                    viewHolder.addfriend.setTextColor(Color.BLACK);
                    FirebaseDatabase.getInstance().getReference("sentRequests/"+FirebaseAuth.getInstance().getUid()).child(users.get(i).getUserId()).setValue("true");
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            System.out.println("---1");
            try {
                profilePic = itemView.findViewById(R.id.profilepic);
                name = itemView.findViewById(R.id.name);
                addfriend = itemView.findViewById(R.id.addfriend);
                report = itemView.findViewById(R.id.report);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
