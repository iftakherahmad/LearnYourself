package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;


public class Home_Fragment extends Fragment {
     PDFView pdfView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                             View view =inflater.inflate(R.layout.fragment_home,container,false);
                             pdfViewer(view);
                             return view;

    }

    private void pdfViewer(View view){
        pdfView=view.findViewById(R.id.pdfview);
        pdfView.fromAsset("database.pdf").load();
    }


}
