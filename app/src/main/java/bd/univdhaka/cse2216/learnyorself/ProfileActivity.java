package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class ProfileActivity extends Activity {
    private RadioRealButtonGroup buttonGroup;
    private FirebaseAuth firebaseAuth;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            firebaseAuth = FirebaseAuth.getInstance();
            setContentView(R.layout.activity_profile);
            userId = getIntent().getStringExtra("userId");
            setTitle(userId + "'s Profile");
            buttonGroup = findViewById(R.id.buttongroup);
            final Bundle bundle = new Bundle();
            bundle.putString("userId", userId);
            buttonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
                @Override
                public void onClickedButton(RadioRealButton button, int position) {
                    Fragment fragment;
                    if (position == 0) {
                        try {
                            fragment = new P_A_AboutFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_frame_layout_id, fragment);
                            fragmentTransaction.commit();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else if(position==1){
                        try{
                            fragment=new P_A_FriendsFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_frame_layout_id,fragment);
                            fragmentTransaction.commit();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
