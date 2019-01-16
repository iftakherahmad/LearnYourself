package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineViewAdapter extends RecyclerView.Adapter<TimelineViewAdapter.ViewHolder>{
    Context context;
    ArrayList<timeline_object> posts=new ArrayList<>();
    TextView description;
    private String parentId;
    public TimelineViewAdapter(ArrayList<timeline_object> posts,Context context){
        this.posts=posts;
        this.context=context;

    }
    public TimelineViewAdapter(ArrayList<timeline_object> posts,Context context,String parentId){
        this.posts=posts;
        this.context=context;
        this.parentId=parentId;
    }


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
                        intent.putExtra("type","vedios");
                        intent.putExtra("parentId",posts.get(i).getPostId());
                        context.startActivity(intent);
                    }

                }
            });
        }
        else if(posts.get(i).getPostType().equals("QUESTION")){/////////////////this part is for question
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
        else if(posts.get(i).getPostType().equals("ANSER")){
            if(posts.get(i).getProfilePic()!=null || posts.get(i).getProfilePic().length()>3)Glide.with(context).asBitmap().load(posts.get(i).getProfilePic()).into(viewHolder.profilePic);
            viewHolder.owner.setText(posts.get(i).getOwnerName());
            viewHolder.postingDate.setText(posts.get(i).getDate());
            System.out.println("....................------------------------------.................++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,PresentAComment.class);
                    intent.putExtra("type","comments");
                    intent.putExtra("postId",posts.get(i).getPostId());
                    intent.putExtra("parentId",parentId);
                    context.startActivity(intent);
                }
            });
            if(!(posts.get(i).getContentImage().equals("") || posts.get(i).getContentImage()==null))Glide.with(context).asBitmap().load(posts.get(i).getContentImage()).into(viewHolder.contentImage);

            viewHolder.details.setText(posts.get(i).getText());
        }
        viewHolder.writeAnser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,CommentActivity.class);
                System.out.println("Posts...........+++++++++++++++++++++++++++"+posts.get(i).getPostId());
                intent.putExtra("path",posts.get(i).getPath());
                intent.putExtra("postId",posts.get(i).getPostId());
                intent.putExtra("parentId",posts.get(i).getPostId());
                intent.putExtra("postType",posts.get(i).getPostType());
                context.startActivity(intent);
            }
        });
        if(!posts.get(i).getPostType().equals("ANSER")) {
            viewHolder.ansers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowComments.class);
                    if(posts.get(i).getPostType().equals("VIDEO"))
                        intent.putExtra("type","vedios");
                    else
                        intent.putExtra("type", "questions");
                    intent.putExtra("path",posts.get(i).getPath());
                    intent.putExtra("postId",posts.get(i).getPostId());
                    intent.putExtra("parentId", posts.get(i).getPostId());
                    context.startActivity(intent);
                }
            });
        }
        else {
            viewHolder.ansers.setVisibility(View.GONE);
        }
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(posts.get(i).getPath());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                viewHolder.likeNumber.setText((String.valueOf( dataSnapshot.child("like").getChildrenCount())));
                if(dataSnapshot.child("like").hasChild(FirebaseAuth.getInstance().getUid())){
                    viewHolder.like.setBackgroundColor(Color.BLUE);
                }
                viewHolder.dislikeNumber.setText(String.valueOf(dataSnapshot.child("dislike").getChildrenCount()));
                if(dataSnapshot.child("dislike").hasChild(FirebaseAuth.getInstance().getUid())){
                    viewHolder.dislike.setBackgroundColor(Color.BLUE);
                }
                if(posts.get(i).getPostType().equals("QUESTION")){
                    viewHolder.commentNumber.setText(String.valueOf(dataSnapshot.child("ansers").getChildrenCount()));
                }
                if(posts.get(i).getPostType().equals("VIDEO")){
                    viewHolder.commentNumber.setText(String.valueOf(dataSnapshot.child("comments").getChildrenCount()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.like.setBackgroundColor(Color.BLUE);
                updateDatabaseForLike(posts.get(i).getPath());
            }
        });
        viewHolder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.dislike.setBackgroundColor(Color.BLUE);
                updateDatabaseForDislike(posts.get(i).getPath());
            }
        });
       // updateLikeColor(posts.get(i).getPath(),viewHolder.like);


    }


    private void updateDatabaseForDislike(String path) {
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(path);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        databaseReference.child("dislike").child(firebaseAuth.getUid()).setValue("true");
    }

    private void updateDatabaseForLike(String path) {
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(path);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        databaseReference.child("like").child(firebaseAuth.getUid()).setValue("true");

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
        TextView likeNumber;
        TextView dislikeNumber;
        TextView commentNumber;
        ImageButton like;
        ImageButton dislike;
        Button ansers;
        ImageButton writeAnser;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.profilepic);
            owner=itemView.findViewById(R.id.postowner);
            likeNumber=itemView.findViewById(R.id.likenumber);
            dislikeNumber=itemView.findViewById(R.id.dislikenumber);
            commentNumber=itemView.findViewById(R.id.commentnumber);
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
