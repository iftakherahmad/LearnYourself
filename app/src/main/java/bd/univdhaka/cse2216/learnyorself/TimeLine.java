package bd.univdhaka.cse2216.learnyorself;

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


public class TimeLine extends Fragment {
     RecyclerView recyclerView;
    TimelineViewAdapter adapter;
    Context context;
    ArrayList<String> profilepics=new ArrayList<>();
    ArrayList<String> owners=new ArrayList<>();
    ArrayList<String>  postingDates=new ArrayList<>();
    ArrayList<String> contentImages=new ArrayList<>();
    ArrayList<String> details=new ArrayList<>();
    ArrayList<String> postTypes=new ArrayList<>();
    ArrayList<String> postIds=new ArrayList<>();
    ArrayList<String> titleTags=new ArrayList<>();
    DatabaseReference posts;
    private ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_time_line, container, false);
        posts= FirebaseDatabase.getInstance().getReference("questions");
        progressBar =view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerHandler(view);

        return view;
    }

    private void recyclerHandler(View view){

        recyclerView=view.findViewById(R.id.recyclerView);
        System.out.println("....1");
        adapter=new TimelineViewAdapter(postIds,postTypes,getActivity(),profilepics,owners,postingDates,contentImages,details);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        System.out.println("........2");
        recyclerView.setLayoutManager(layoutManager);
        fetchData();
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int totalItem=layoutManager.getItemCount();
//                System.out.println(".......3");
//                int scrolledOutItem=layoutManager.findFirstVisibleItemPosition();
//                int onScreen=layoutManager.getChildCount();
//                if(totalItem<=scrolledOutItem+onScreen){
//                    System.out.println(".........4");
//                    fetchData();
//                }
//            }
//        });


    }

    private void fetchData() {
        posts.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                for (DataSnapshot dataSnapshot : dataSnapshot1.getChildren()){
                    System.out.println("........5");

                final String owner = dataSnapshot.child("owner").getValue().toString();
                String postingDate = dataSnapshot.child("time").getValue().toString();
                postingDates.add(postingDate);
                final String textUrl = dataSnapshot.child("textUrl").getValue().toString();
                String imageUrls = dataSnapshot.child("imageUrls").getValue().toString();
                String firstImageUrl = extractFirstUrl(imageUrls);
                contentImages.add(firstImageUrl);
                System.out.println(".........6");
                DatabaseReference userTable = FirebaseDatabase.getInstance().getReference("users/"+owner);
                final String titleTag = "#" + dataSnapshot.child("tag").getValue().toString() + ": \n" + dataSnapshot.child("title").getValue().toString() + "\n";
                userTable.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        owners.add(name);
                        System.out.println(".......7");
                        String profilePicUrl = dataSnapshot.child("profilePicUrl").getValue().toString();
                        profilepics.add(profilePicUrl);
                        postTypes.add("QUESTION");
                        progressBar.setVisibility(View.GONE);
                        new BackGroundTask(details, textUrl, titleTag, recyclerView, adapter, getActivity()).execute();
                        System.out.println("..........8");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private String extractFirstUrl(String imageUrls) {
        Scanner scanner=new Scanner(imageUrls);
        int urlLength=scanner.nextInt();
        String remaining=scanner.nextLine();
        String url1=remaining.substring(1,urlLength);
        return url1;
    }








}
