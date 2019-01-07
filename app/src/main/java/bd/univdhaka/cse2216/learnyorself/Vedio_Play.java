package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
//...............................................................................\\\\......////

public class Vedio_Play extends Activity {
    private VideoView videoView;
    private ProgressBar progressBar;
    private Scanner scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio__play);
        progressBar=findViewById(R.id.progressbar);
        MediaController mController=new MediaController(this);
        mController.setAnchorView(videoView);
        videoView=findViewById(R.id.vedioview);
       // videoView.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        String url=getIntent().getStringExtra("url");
        startStreaming(mController,url);


    }


    private void startStreaming(MediaController mController,String url) {
        videoView.setMediaController(mController);
        videoView.setVideoPath(url);
        videoView.requestFocus();
        videoView.setZOrderMediaOverlay(true);

        videoView.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                videoView.setBackgroundColor(Color.TRANSPARENT);
                videoView.start();
            }
        });
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if(what==MediaPlayer.MEDIA_INFO_BUFFERING_END){
                    progressBar.setVisibility(View.GONE);
                    return true;
                }
                else if(what==MediaPlayer.MEDIA_INFO_BUFFERING_START){
                    progressBar.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }

}
