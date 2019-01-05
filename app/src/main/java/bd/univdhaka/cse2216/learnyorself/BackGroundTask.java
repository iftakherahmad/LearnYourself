package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class BackGroundTask extends AsyncTask<Void,Void,Void> {

    RecyclerView recyclerView;
    TimelineViewAdapter adapter;
    ArrayList<String> description;
    private Context context;
    String titleTag;
    String url;
    public BackGroundTask( ArrayList<String> description,String url,String titleTag,RecyclerView recyclerView, TimelineViewAdapter adapter, Context context) {
        this.recyclerView = recyclerView;
        this.url=url;
        this.description=description;
        this.titleTag=titleTag;
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        System.out.println("..........10");
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String str=getFirst100Characters(url);
        System.out.println("............11");
        String result=titleTag+str;
        System.out.println("back:"+result);
        System.out.println("..........12");
        description.add(result);
        publishProgress();
        System.out.println("............13");
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        adapter.notifyDataSetChanged();
    }

    private String getFirst100Characters(String s)  {
        System.out.println("url.........."+s);
        String received="";
        try {
            System.out.println("k");
            URL url=new URL(s);
            System.out.println("kkk");
            Scanner scanner=new Scanner(url.openStream());

            int cnt=0;
            while(scanner.hasNext()){
                cnt++;
                System.out.println("cnt:"+cnt);

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
        System.out.println("rec....."+received);

        return received;
    }

}
