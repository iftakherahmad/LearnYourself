package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationViewAdapter extends RecyclerView.Adapter<NotificationViewAdapter.ViewHolder>{
    ArrayList<NotificationObject> notifications=new ArrayList<>();
    Context context;

    public NotificationViewAdapter(ArrayList<NotificationObject> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.notification_sample,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final  int i) {
     //   viewHolder.description.setText(descriptions.get(i));
        try{
            DatabaseReference userTable=FirebaseDatabase.getInstance().getReference("users/"+notifications.get(i).getInvolvedPerson());
            userTable.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String  userName=dataSnapshot.child("name").getValue().toString();
                    String profilepic=dataSnapshot.child("profilePicUrl").getValue().toString();
                    Glide.with(context).asBitmap().load(profilepic).into(viewHolder.profilePic);//
                    DatabaseReference postTable;
                    if(notifications.get(i).getPostType().equals("QUESTION")) {
                        postTable = FirebaseDatabase.getInstance().getReference("questions/" + notifications.get(i).getInvolvedPost());
                    }
                    else{
                        postTable=FirebaseDatabase.getInstance().getReference("vedios/"+notifications.get(i).getInvolvedPost());
                    }
                    postTable.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String postTitle=dataSnapshot.child("title").getValue().toString();
                            String description=userName +notifications.get(i).getMessage()+postTitle;
                            viewHolder.description.setText(description);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            viewHolder.date.setText(notifications.get(i).getTime());
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePic;
        TextView description;
        TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.profilepic);
            description=itemView.findViewById(R.id.description);
            date=itemView.findViewById(R.id.date);
        }
    }
}
