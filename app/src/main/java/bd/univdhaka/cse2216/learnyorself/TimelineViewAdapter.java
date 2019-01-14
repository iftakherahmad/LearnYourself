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
//    ArrayList<String> profilepics=new ArrayList<>();
//    ArrayList<String> owners=new ArrayList<>();
//    ArrayList<String>  postingDates=new ArrayList<>();
//    ArrayList<String> contentImages=new ArrayList<>();
//    ArrayList<String> details=new ArrayList<>();
//    ArrayList<String> types=new ArrayList<>();
//    ArrayList<String> postIds=new ArrayList<>();
    ArrayList<timeline_object> posts=new ArrayList<>();
    TextView description;
    public TimelineViewAdapter(ArrayList<timeline_object> posts,Context context){
        this.posts=posts;
        this.context=context;
    }

//    public TimelineViewAdapter(ArrayList<String> postIds,ArrayList<String> types,Context context,ArrayList<String> profilepics,ArrayList<String> owners,ArrayList<String> postingDates
//    ,ArrayList<String> contentImages,ArrayList<String> details
//    ){
//        this.postIds=postIds;
//        this.types=types;
//        this.context=context;
//        this.profilepics=profilepics;
//        this.owners=owners;
//        this.postingDates=postingDates;
//        this.contentImages=contentImages;
//        this.details=details;
//
//    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.timeline_sample,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if(posts.get(i).getPostType().equals("VIDEO")) {
            Glide.with(context).asBitmap().load(posts.get(i).getProfilePic()).into(viewHolder.profilePic);
            viewHolder.owner.setText(posts.get(i).getOwnerName());
            viewHolder.postingDate.setText(posts.get(i).getDate());
            viewHolder.details.setText(posts.get(i).getText());
            Glide.with(context).asBitmap().load(posts.get(i).getContentImage()).into(viewHolder.contentImage);
            viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        Intent intent = new Intent(context, Vedio_Play.class);
                        intent.putExtra("url", posts.get(i).getContentImage());
                        context.startActivity(intent);
                    }

                }
            });
        }
        else if(posts.get(i).getPostType().equals("QUESTION")){
          //  System.out.println("first tut tut");
            if(posts.get(i).getProfilePic()!=null || posts.get(i).getProfilePic().length()>3)Glide.with(context).asBitmap().load(posts.get(i).getProfilePic()).into(viewHolder.profilePic);
          //  System.out.println("tut tut 0");
            viewHolder.owner.setText(posts.get(i).getOwnerName());
           // System.out.println("tut tut1");
            viewHolder.postingDate.setText(posts.get(i).getDate());
           // System.out.println("tut tut2");
            viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ViewPost.class);
                    intent.putExtra("type","questions");
                    intent.putExtra("postId",posts.get(i).getPostId());
                    context.startActivity(intent);
                }
            });
          //  System.out.println("tut tut5");
            if(!(posts.get(i).getContentImage().equals("") || posts.get(i).getContentImage()==null))Glide.with(context).asBitmap().load(posts.get(i).getContentImage()).into(viewHolder.contentImage);
          //  System.out.println("tut tut6");
            viewHolder.details.setText(posts.get(i).getText());

        }
        viewHolder.writeAnser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,CommentActivity.class);
                intent.putExtra("postId",posts.get(i).getPostId());
                context.startActivity(intent);
            }
        });
        viewHolder.ansers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ShowComments.class);
                intent.putExtra("type","questions");
                intent.putExtra("parentId",posts.get(i).getPostId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
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
