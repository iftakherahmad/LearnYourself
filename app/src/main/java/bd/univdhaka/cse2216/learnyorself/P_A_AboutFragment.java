package bd.univdhaka.cse2216.learnyorself;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class P_A_AboutFragment extends Fragment {
    private ImageView profilePicView;
    private EditText nameView;
    private TextView userNameView;
    private TextView emailView;
    private EditText institutionView;
    private Spinner professionView;
    private EditText oldPasswordView;
    private EditText newPasswordView;
    private EditText confirmPasswordView;
    private Button confirmButton;
    private DatabaseReference userTable;
    private FirebaseAuth authenticator;
    private String userId;
    private Context context;
    private LinearLayout oldPasswd;
    private LinearLayout newPasswd;
    private LinearLayout confirmPasswd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_p__a__about, container, false);
            profilePicView = view.findViewById(R.id.profilepic);
            nameView = view.findViewById(R.id.name);
            userNameView = view.findViewById(R.id.username);
            emailView = view.findViewById(R.id.email);
            context = view.getContext();
            institutionView = view.findViewById(R.id.institution);
            professionView = view.findViewById(R.id.profession);
            oldPasswordView = view.findViewById(R.id.oldpassword);
            newPasswordView = view.findViewById(R.id.newpassword);
            confirmPasswordView = view.findViewById(R.id.confirmpassword);
            confirmButton = view.findViewById(R.id.update);
            oldPasswd = view.findViewById(R.id.oldpasswd);
            newPasswd = view.findViewById(R.id.newpasswd);
            confirmPasswd = view.findViewById(R.id.confirmpasswd);
            authenticator = FirebaseAuth.getInstance();
            Bundle bundle =this.getArguments();
            if(bundle!=null)userId = bundle.getString("userId");
            System.out.println(userId);
            userId=getActivity().getIntent().getStringExtra("userId");
            System.out.println("uuuuu:"+userId);

            String child = authenticator.getCurrentUser().getUid();
            if (!userId.equals(child)) {
                oldPasswd.setVisibility(View.GONE);
                newPasswd.setVisibility(View.GONE);
                confirmPasswd.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
            }
            Toast.makeText(context, child, Toast.LENGTH_SHORT).show();

            userTable = FirebaseDatabase.getInstance().getReference("users/" + userId);
            if (userTable != null) {
                userTable.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userName = dataSnapshot.child("name").getValue().toString();

                        String email = dataSnapshot.child("email").getValue().toString();
//                      Toast.makeText(context,email,Toast.LENGTH_SHORT).show();
                        String institution = dataSnapshot.child("institution").getValue().toString();
                        String profession = dataSnapshot.child("profession").getValue().toString();
                        String profilePicUrl = dataSnapshot.child("profilePicUrl").getValue().toString();
                        setData(userName, email, institution, profession, profilePicUrl);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else Toast.makeText(context, "databaseReferencenull", Toast.LENGTH_SHORT).show();


            return view;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void setData(String userName, String email, String institution, String profession, String profilePicUrl) {
        userNameView.setText(userName);
        nameView.setText(userName);
        emailView.setText(email);
        institutionView.setText(institution);
        professionView.setSelection(((ArrayAdapter)professionView.getAdapter()).getPosition(profession));
        Glide.with(context).asBitmap().load(profilePicUrl).into(profilePicView);
        if(!userId.equals(authenticator.getUid())) {
            nameView.setFocusable(false);
           // emailView.setFocusable(false);
            institutionView.setFocusable(false);
            professionView.setClickable(false);
            professionView.setFocusable(false);

        }

    }

}
