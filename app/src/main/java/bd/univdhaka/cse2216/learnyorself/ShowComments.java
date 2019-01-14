
package bd.univdhaka.cse2216.learnyorself;

        import android.app.Activity;
        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.app.Fragment;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ProgressBar;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.Scanner;

        import static java.lang.Thread.sleep;


public class ShowComments extends Activity {
    RecyclerView recyclerView;
    TimelineViewAdapter adapter;
    Context context;
    //    ArrayList<String> profilepics=new ArrayList<>();
//    ArrayList<String> owners=new ArrayList<>();
//    ArrayList<String>  postingDates=new ArrayList<>();
//    ArrayList<String> contentImages=new ArrayList<>();
//    ArrayList<String> details=new ArrayList<>();
//    ArrayList<String> postTypes=new ArrayList<>();
//    ArrayList<String> postIds=new ArrayList<>();
//    ArrayList<String> titleTags=new ArrayList<>();
    ArrayList<timeline_object> timeline_posts=new ArrayList<>();
    DatabaseReference comments;
    private int index=0;
    private ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    private String parentId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_comments);
        comments= FirebaseDatabase.getInstance().getReference("comments");
        parentId=getIntent().getStringExtra("parentId");
        progressBar =findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerHandler();


    }

    private void recyclerHandler(){

        recyclerView=findViewById(R.id.recyclerView);
        System.out.println("....1");
        //adapter=new TimelineViewAdapter(postIds,postTypes,getActivity(),profilepics,owners,postingDates,contentImages,details);
        adapter=new TimelineViewAdapter(timeline_posts,this);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        System.out.println("........2");
        recyclerView.setLayoutManager(layoutManager);
        fetchData();

    }

    private void fetchData() {
        comments.child(parentId).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                index=-1;


                for (DataSnapshot dataSnapshot : dataSnapshot1.getChildren()){
                    index++;
//                    profilepics.add("");
//                    owners.add("");
//                    postingDates.add("");
//                    contentImages.add("");
//                    postTypes.add("");
//                    postIds.add("");
//                    titleTags.add("");
                    System.out.println("........5");
                    timeline_object post=new timeline_object();

                    final String owner = dataSnapshot.child("owner").getValue().toString();
                    String postingDate = dataSnapshot.child("time").getValue().toString();
                    post.setDate(postingDate);
                    //postingDates.add(postingDate);
                    // postingDates.set(index,postingDate);
                    System.out.println("ind.."+index+"postingDate......."+postingDate);
                    String postId=dataSnapshot.getKey();
                    post.setPostId(postId);
                    //postIds.add(postId);
                    System.out.println("index:"+index);
                    // postIds.set(index,postId);
                    System.out.println("ind..."+index+"postId...."+postId);
                    final String textUrl = dataSnapshot.child("textUrl").getValue().toString();
                    String imageUrls = dataSnapshot.child("imageUrls").getValue().toString();
                    String firstImageUrl = extractFirstUrl(imageUrls);
                    //contentImages.add(firstImageUrl);
                    post.setContentImage(firstImageUrl);
                    //    contentImages.set(index,firstImageUrl);
                    System.out.println(".........6");
                    DatabaseReference userTable = FirebaseDatabase.getInstance().getReference("users/"+owner);

                    //final String titleTag = "#" + dataSnapshot.child("tag").getValue().toString() + ": \n" + dataSnapshot.child("title").getValue().toString() + "\n";
                    addListenerInUserTable(userTable,post,textUrl,index);
//                userTable.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String name = dataSnapshot.child("name").getValue().toString();
//                        System.out.println("...................................hh");
//                        //owners.add(name);
//                        System.out.println("index............"+index);
//                        owners.set(index,name);
//                        System.out.println(".......7");
//                        String profilePicUrl = dataSnapshot.child("profilePicUrl").getValue().toString();
//                        //profilepics.add(profilePicUrl);
//                        profilepics.set(index,profilePicUrl);
//                       // postTypes.add("QUESTION");
//                        postTypes.set(index,"QUESTION");
//                        progressBar.setVisibility(View.GONE);
//                        new BackGroundTask(details, textUrl, titleTag, recyclerView, adapter, getActivity()).execute();
//                        System.out.println("..........8");
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addListenerInUserTable(DatabaseReference userTable, final timeline_object post, final String textUrl, final int idx){

        userTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                while(idx!=timeline_posts.size()){
//                    try {
//                        sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                String name = dataSnapshot.child("name").getValue().toString();
                System.out.println("...............................hh");
                //  owners.add(name);
                post.setOwnerName(name);
                System.out.println("idx............"+idx);
                //  owners.set(idx,name);
                System.out.println(".......7");
                String profilePicUrl = dataSnapshot.child("profilePicUrl").getValue().toString();
                //profilepics.add(profilePicUrl);
                post.setProfilePic(profilePicUrl);
                //profilepics.set(idx,profilePicUrl);
                // postTypes.add("QUESTION");
                post.setPostType("QUESTION");
                //postTypes.set(index,"QUESTION");
                progressBar.setVisibility(View.GONE);
                String titleTag="";
                System.out.println("start,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+idx);
                new BackGroundTask(timeline_posts,idx,post, textUrl, titleTag, recyclerView, adapter, ShowComments.this).execute();
                System.out.println(".........8");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private String extractFirstUrl(String imageUrls) {
            if(imageUrls.length()<5)return "";
        Scanner scanner=new Scanner(imageUrls);
        int urlLength=scanner.nextInt();
        String remaining=scanner.nextLine();
        String url1=remaining.substring(1,urlLength);
        return url1;
    }








}

