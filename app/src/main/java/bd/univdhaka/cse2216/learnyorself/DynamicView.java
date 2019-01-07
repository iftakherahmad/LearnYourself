package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DynamicView {
    Context context;

    public DynamicView(Context context) {
        this.context = context;
    }
    public TextView createTextView(String text,int textSize){
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView=new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(Color.BLACK);
        return textView;
    }
    public EditText createEditText(){
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        EditText editText=new EditText(context);
        editText.setLayoutParams(layoutParams);
        editText.setTextSize(18);
        editText.setTextColor(Color.BLACK);
        editText.setText(" ");
        return editText;
    }
    public ImageView createImageView(String url){
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView imageView=new ImageView(context);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if(url!=null)
        Glide.with(context).asBitmap().load(url).into(imageView);
        return imageView;
    }
}
