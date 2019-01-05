package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class Notification extends Fragment {

    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    NotificationViewAdapter adapter;
    ArrayList<String> profilePics=new ArrayList<String>();
    ArrayList<String> descriptions=new ArrayList<String>();
    ArrayList<String> dates=new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_notification, container, false);

        dataSetter();
        processRecyclerView(view);
        return view;
    }
    private void processRecyclerView( View view){
        recyclerView=view.findViewById(R.id.recyclerView);
        if(recyclerView==null){
            Toast.makeText(getActivity(),"recyclerView is null",Toast.LENGTH_SHORT).show();
        }
        else {
            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new NotificationViewAdapter(getActivity(), profilePics, descriptions, dates);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
    private  void dataSetter(){
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        profilePics.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        descriptions.add("description");
        descriptions.add("description");
        descriptions.add("description");
        descriptions.add("description");
        descriptions.add("description");
        descriptions.add("description");
        descriptions.add("description");
        descriptions.add("description");
        descriptions.add("description");
        descriptions.add("description");
        dates.add("10/2/2019");
        dates.add("10/2/2019");
        dates.add("10/2/2019");
        dates.add("10/2/2019");
        dates.add("10/2/2019");
        dates.add("10/2/2019");
        dates.add("10/2/2019");
        dates.add("10/2/2019");
        dates.add("10/2/2019");
        dates.add("10/2/2019");

    }
}
