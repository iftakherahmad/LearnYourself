package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerViewAdapter extends  RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> {

    private static  final String TAG="RecyclerViewAdapter";
    private ArrayList<User> users=new ArrayList<>();
    private Context context;

    public recyclerViewAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.testlayout,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        try {
            Glide.with(context).
                    asBitmap().
                    load(users.get(i).getProfilePicUrl()).
                    into(viewHolder.image);
            viewHolder.textView.setText(users.get(i).getName());
            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, users.get(i).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewHolder.confirmButton.getText().equals("CONFIRM")){
                        FirebaseDatabase.getInstance().getReference("friends/"+ FirebaseAuth.getInstance().getUid()).child(users.get(i).getUserId()).setValue("true");//adding to my friend list
                        FirebaseDatabase.getInstance().getReference("requests/"+FirebaseAuth.getInstance().getUid()).child(users.get(i).getUserId()).removeValue();// removing from hanging request
                        FirebaseDatabase.getInstance().getReference("friends/"+ users.get(i).getUserId()).child(FirebaseAuth.getInstance().getUid()).setValue("true");// adding my id to friend's friend list
                        FirebaseDatabase.getInstance().getReference("sentRequests/"+users.get(i).getUserId()).child(FirebaseAuth.getInstance().getUid()).removeValue();//removing from friend's sent request list
                        viewHolder.confirmButton.setText("FRIEND");
                        viewHolder.confirmButton.setTextColor(Color.BLACK);
                        viewHolder.confirmButton.setBackgroundColor(Color.WHITE);
                        viewHolder.deleteButton.setVisibility(View.GONE);
                    }
                }
            });
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewHolder.deleteButton.getText().equals("DELETE")) {
                        FirebaseDatabase.getInstance().getReference("requests/" + FirebaseAuth.getInstance().getUid()).child(users.get(i).getUserId()).removeValue();// removing from hanging request
                        FirebaseDatabase.getInstance().getReference("sentRequests/" + users.get(i).getUserId()).child(FirebaseAuth.getInstance().getUid()).removeValue();//removing from friend's sent request list
                        viewHolder.confirmButton.setVisibility(View.GONE);
                        viewHolder.deleteButton.setTextColor(Color.RED);
                        viewHolder.deleteButton.setBackgroundColor(Color.WHITE);
                        viewHolder.deleteButton.setText("DELETED");
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
        CircleImageView image;
        TextView textView;
        RelativeLayout relativeLayout;
        Button confirmButton;
        Button deleteButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profilepic);
            textView=itemView.findViewById(R.id.textview);
            relativeLayout=itemView.findViewById(R.id.parentlayout);
            confirmButton=itemView.findViewById(R.id.addbutton);
            deleteButton=itemView.findViewById(R.id.deletebutton);
        }
    }
}
