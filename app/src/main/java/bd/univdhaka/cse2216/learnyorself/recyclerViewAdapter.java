package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerViewAdapter extends  RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> {

    private static  final String TAG="RecyclerViewAdapter";
    private ArrayList<String> imageNames=new ArrayList<>();
    private ArrayList<String> images =new ArrayList<>();
    private Context context;

    recyclerViewAdapter(Context context,ArrayList<String> imageNames,ArrayList<String> images){
        this.context=context;
        this.imageNames=imageNames;
        this.images=images;
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.testlayout,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
      Glide.with(context).
                asBitmap().
                load(images.get(i)).
                into(viewHolder.image);
        viewHolder.textView.setText(imageNames.get(i));
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,imageNames.get(i),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {

        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView textView;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profilepic);
            textView=itemView.findViewById(R.id.textview);
            relativeLayout=itemView.findViewById(R.id.parentlayout);
        }
    }
}
