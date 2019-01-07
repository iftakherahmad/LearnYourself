package bd.univdhaka.cse2216.learnyorself;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

import static java.lang.Math.min;


public class LoginActivity5 extends Activity {
    private RadioRealButtonGroup buttonGroup;
    private Menu menu;
    private FirebaseAuth authenticator;
    private DatabaseReference database;
    private Context context;
    @Override
    public void onBackPressed() {
        doNothing();
    }

    private void doNothing() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticator=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login5);
        context=this;

        setTitle(getResources().getString(R.string.title));



       // Toast.makeText(this,"hello babu djfndsjfn",Toast.LENGTH_SHORT).show();
        buttonGroup=findViewById(R.id.buttongroup);

        buttonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                Fragment fragment;

                    if(position==0){
                      //  Toast.makeText(LoginActivity5.this,"came in 0",Toast.LENGTH_SHORT).show();

                        fragment = new Home_Fragment();
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_frame_layout_id,fragment);
                     //   fragmentTransaction.addToBackStack();
                        fragmentTransaction.commit();
                    }
                    else if(position==1){
                      //  Toast.makeText(LoginActivity5.this,"came in 1",Toast.LENGTH_SHORT).show();

                        fragment = new TimeLine();
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_frame_layout_id,fragment);
                        fragmentTransaction.commit();
                    }
                    else if(position==2){
                       // Toast.makeText(LoginActivity5.this,"came in 2",Toast.LENGTH_SHORT).show();

                        fragment = new Notification();
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_frame_layout_id,fragment);
                        fragmentTransaction.commit();
                    }
                    else if(position==3){
                      //  Toast.makeText(LoginActivity5.this,"came in 3",Toast.LENGTH_SHORT).show();

                        fragment = new RequestFragment();
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_frame_layout_id,fragment);
                        fragmentTransaction.commit();
                    }

            }
        });


        }



    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu5,menu);
        this.menu=menu;
        MenuItem menuItem=menu.findItem(R.id.username);
        if(authenticator!=null){
            menuItem.setTitle(authenticator.getCurrentUser().getDisplayName());
        }
        else
        Toast.makeText(this,"authenticator null",Toast.LENGTH_SHORT).show();
       // MenuBuilder b=(MenuBuilder) menu;
     //   b.setOptionalIconsVisible(true);
        return super.onCreateOptionsMenu(menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      //  Toast.makeText(this,getClass().getName(),Toast.LENGTH_SHORT).show();
        if(item.getItemId()==R.id.username)
        {
            showProfile();
        }
        if(item.getItemId()==R.id.ask) {
            Intent intent=new Intent(LoginActivity5.this,CreatePost12.class);
            intent.putExtra("postType","questions");
            startActivity(intent);
        }
        if(item.getItemId()==R.id.search) {
            Intent intent=new Intent(context,SearchActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.uploadvideo) {
            Intent intent=new Intent(this,UploadVideo10.class);
            startActivity(intent);

        }
        if(item.getItemId()==R.id.writearticle){
            Intent intent=new Intent(this,ViewPost.class);
            System.out.println("....9999999");
            intent.putExtra("type","questions");
            intent.putExtra("postId","-LVdNpivCEShjRX8gSFc");
            System.out.println(".....4");
            startActivity(intent);
        }
        if(item.getItemId()==R.id.finduser)
            Toast.makeText(this,"Find User is Clicked",Toast.LENGTH_SHORT).show();
        if(item.getItemId()==R.id.about)
            Toast.makeText(this,"About Clicked",Toast.LENGTH_SHORT).show();
        if(item.getItemId()==R.id.logout) {
       //   Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();
           signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProfile() {
        Intent intent5=new Intent(this,ProfileActivity.class);
        startActivity(intent5);
    }

    private void signOut() {
        Intent intent=new Intent(this,MainActivity.class);
        FirebaseAuth myAuth=FirebaseAuth.getInstance();
        myAuth.signOut();
        startActivity(intent);
    }
}
