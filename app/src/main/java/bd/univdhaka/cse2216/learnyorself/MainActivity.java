package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    PDFView pdfView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.title));
        pdfView=findViewById(R.id.pdfview1);
        pdfView.fromAsset("database.pdf").load();
        //Toast.makeText(this,getClass().getName()+" "+"kutub",Toast.LENGTH_SHORT);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        //Toast.makeText(this,"home",Toast.LENGTH_SHORT).show();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.signup) {
            Toast.makeText(this, "Signup Clicked", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,SignUpActivity3.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.login) {
           // Toast.makeText(this, "Log in Clicked", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(this,LoginActivity4.class);
            startActivity(intent1);
        }
        if(item.getItemId()==R.id.search) {
          //  Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,SearchActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.about)
            Toast.makeText(this,"About Clicked",Toast.LENGTH_SHORT).show();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }
}
