package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineViewAdapter extends RecyclerView.Adapter<TimelineViewAdapter.ViewHolder>{
    Context context;
    ArrayList<String> profilepics;
    ArrayList<String> owners;
    ArrayList<String>  postingDates;
    ArrayList<String> contentImages;
    ArrayList<String> details;
    ArrayList<String> types;
    ArrayList<String> postIds;
    TextView description;

    public TimelineViewAdapter(ArrayList<String> postIds,ArrayList<String> types,Context context,ArrayList<String> profilepics,ArrayList<String> owners,ArrayList<String> postingDates
    ,ArrayList<String> contentImages,ArrayList<String> details
    ){
        this.postIds=postIds;
        this.types=types;
        this.context=context;
        this.profilepics=profilepics;
        this.owners=owners;
        this.postingDates=postingDates;
        this.contentImages=contentImages;
        this.details=details;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.timeline_sample,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if(types.get(i).equals("VIDEO")) {
            Glide.with(context).asBitmap().load(profilepics.get(i)).into(viewHolder.profilePic);
            viewHolder.owner.setText(owners.get(i));
            viewHolder.postingDate.setText(postingDates.get(i));
            viewHolder.details.setText(details.get(i));
            Glide.with(context).asBitmap().load(contentImages.get(i)).into(viewHolder.contentImage);
            viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        Intent intent = new Intent(context, Vedio_Play.class);
                        intent.putExtra("url", contentImages.get(i));
                        context.startActivity(intent);
                    }

                }
            });
        }
        else if(types.get(i).equals("QUESTION")){
            System.out.println("first tut tut");
            Glide.with(context).asBitmap().load(profilepics.get(i)).into(viewHolder.profilePic);
            System.out.println("tut tut 0");
            viewHolder.owner.setText(owners.get(i));
            System.out.println("tut tut1");
            viewHolder.postingDate.setText(postingDates.get(i));
            System.out.println("tut tut2");
            System.out.println("tut tut5");
            if(!contentImages.get(i).equals("no image"))Glide.with(context).asBitmap().load(contentImages.get(i)).into(viewHolder.contentImage);
            System.out.println("tut tut6");
            viewHolder.details.setText(details.get(i));

        }

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public void setDeatails(String deatails) {
        description.setText(deatails);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePic;
        TextView owner;
        TextView postingDate;
        ImageView contentImage;
        TextView details;
        ImageButton like;
        ImageButton dislike;
        Button ansers;
        ImageButton writeAnser;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.profilepic);
            owner=itemView.findViewById(R.id.postowner);
            postingDate=itemView.findViewById(R.id.postingdate);
            contentImage=itemView.findViewById(R.id.contentimage);
            details=itemView.findViewById(R.id.details);
            description=details;
            parentLayout=itemView.findViewById(R.id.parentlayout);
            like=itemView.findViewById(R.id.like);
            dislike=itemView.findViewById(R.id.dislike);
            ansers=itemView.findViewById(R.id.ansers);
            writeAnser=itemView.findViewById(R.id.addanser);
        }
    }



}
