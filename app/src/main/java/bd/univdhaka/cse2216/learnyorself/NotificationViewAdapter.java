package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationViewAdapter extends RecyclerView.Adapter<NotificationViewAdapter.ViewHolder>{
    ArrayList<String> profilePics;
    ArrayList<String> descriptions;
    ArrayList<String> dates;
    Context context;
    public NotificationViewAdapter(Context context, ArrayList<String> profilePics,ArrayList<String> descriptions,ArrayList<String> dates){
        this.profilePics=profilePics;
        this.descriptions=descriptions;
        this.dates=dates;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.notification_sample,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.description.setText(descriptions.get(i));
        viewHolder.date.setText(dates.get(i));
        GlideApp.with(context).asBitmap().load(profilePics.get(i)).into(viewHolder.profilePic);
    }

    @Override
    public int getItemCount() {
        return descriptions.size();
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
