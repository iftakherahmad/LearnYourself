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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profile);
        setTitle(firebaseAuth.getCurrentUser().getDisplayName()+"'s Profile");
//        buttonGroup=findViewById(R.id.buttongroup);
//        buttonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
//            @Override
//            public void onClickedButton(RadioRealButton button, int position) {
//                Fragment fragment;
////                if(position==0){
////                    fragment=new P_A_AboutFragment();
////                    FragmentManager fragmentManager=getFragmentManager();
////                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
////                    fragmentTransaction.replace(R.id.fragment_frame_layout_id,fragment);
////                    fragmentTransaction.commit();
////                }
//            }
//        });
    }
}
