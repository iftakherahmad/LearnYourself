package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class BackGroundTask extends AsyncTask<Void,Void,Void> {

    RecyclerView recyclerView;
    TimelineViewAdapter adapter;
    private Context context;
    timeline_object post;
    String titleTag;
    ArrayList<timeline_object> posts;
    String url;
    int index;
    public BackGroundTask( ArrayList<timeline_object> posts,int index,timeline_object post,String url,String titleTag,RecyclerView recyclerView, TimelineViewAdapter adapter, Context context) {
        this.recyclerView = recyclerView;
        this.url=url;
        this.index=index;
        this.post=post;
        this.posts=posts;
        this.titleTag=titleTag;
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
       // System.out.println("..........10");
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        System.out.println("intddd--------------"+index);
    //    System.out.println(index+" .................................................................... "+description.size());
//        while(description.size()!=index){
//            try {
//                System.out.println("index: -"+index+" size-----"+description.size());
//                sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    //    System.out.println("end------------------------------------------------------------------------"+index);
        String str=getFirst100Characters(url);
       // System.out.println("............11");
        String result=titleTag+str;
        //System.out.println("back:"+result);
      //  System.out.println("..........12");
    //    System.out.println("desss............................................................................."+index);
       // description.add(result);
        post.setText(result);
        posts.add(post);
        System.out.println("1 added");

        publishProgress();
      //  System.out.println("............13");
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        adapter.notifyDataSetChanged();
    }

    private String getFirst100Characters(String s)  {
        //System.out.println("url.........."+s);
        String received="";
        try {
          //  System.out.println("k");
            URL url=new URL(s);
         //   System.out.println("kkk");
            Scanner scanner=new Scanner(url.openStream());

            int cnt=0;
            while(scanner.hasNext()){
                cnt++;
              //  System.out.println("cnt:"+cnt);

                String str=scanner.next();
                if(!str.equals("<sPlIt.1>")) received+=str+" ";
                else received+="\n";
                cnt+=str.length();
                if(cnt>110){
                    if(scanner.hasNext())received+=" ...more";
                    break;}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
     //   System.out.println("rec....."+received);

        return received;
    }



}
