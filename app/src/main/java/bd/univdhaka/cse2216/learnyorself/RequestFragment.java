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

import java.util.ArrayList;


public class RequestFragment extends Fragment {

    private ArrayList<String> imageNames=new ArrayList<>();
    private ArrayList<String> images=new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_request, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        initImageBitmap();
        return view;
    }

    private  void RecyclerHandler(){

        recyclerViewAdapter adapter=new recyclerViewAdapter(getActivity(),imageNames,images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    private  void initImageBitmap(){
        images.add("https://images3.alphacoders.com/823/thumb-1920-82317.jpg");
        imageNames.add("Yeah worked");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("https://image.ibb.co/gjxGV0/IMG-20170623-111439.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("https://image.ibb.co/gjxGV0/IMG-20170623-111439.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("https://image.ibb.co/gjxGV0/IMG-20170623-111439.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("https://image.ibb.co/gjxGV0/IMG-20170623-111439.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("https://image.ibb.co/gjxGV0/IMG-20170623-111439.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("https://image.ibb.co/gjxGV0/IMG-20170623-111439.jpg");
        imageNames.add("Md. Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Iftakher Ahmad");
        images.add("http://www.sibleyguides.com/wp-content/uploads/American-Goldfinch_20160401_web.jpg");
        imageNames.add("Iftakher Ahmad");

        //   images.add("https://drive.google.com/open?id=0B9Qgxpr2sc3eR1FnRGJ3Ty16RnVCTGJFbmxQNG5oMG8wZllj");
        //   imageNames.add("image2");
        RecyclerHandler();
    }

}
