package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.zip.Inflater;

public class SplashScreen0 extends Activity {
    private ProgressBar progressBar;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen0);
        imageView=findViewById(R.id.imageView);
        setTitle("Learn Yourself");
        GlideApp.with(this).asBitmap().load(R.mipmap.home).into(imageView);


        progressBar=findViewById(R.id.progressbar);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=100;i++){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.setProgress(i);
                }
                Intent intent=new Intent(SplashScreen0.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        thread.start();

    }

    @Override
    public void onBackPressed() {
    }
}
