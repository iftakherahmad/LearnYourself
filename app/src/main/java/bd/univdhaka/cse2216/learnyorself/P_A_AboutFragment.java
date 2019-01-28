package bd.univdhaka.cse2216.learnyorself;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


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
    private ImageButton confirmButton;
    private DatabaseReference userTable;
    private FirebaseAuth authenticator;
    private String userId;
    private Context context;
    private LinearLayout oldPasswd;
    private LinearLayout newPasswd;
    private LinearLayout confirmPasswd;
    private ProgressDialog progressDialog;
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
            progressDialog=new ProgressDialog(context);
            progressDialog.setTitle("Updating...");

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
            else{
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateProfile();
                    }
                });
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

    private void updateProfile() {
        progressDialog.show();
        final String name=nameView.getText().toString();
        String profession=professionView.getSelectedItem().toString();
        String institution=institutionView.getText().toString();
        String oldPassword=oldPasswordView.getText().toString();
        final String email=emailView.getText().toString();
        final String newPassword=newPasswordView.getText().toString();
        String confirmPassword=confirmPasswordView.getText().toString();
        final Map updateProfile=new HashMap();
        updateProfile.put("users/"+userId+"/name",name);
        updateProfile.put("users/"+userId+"/profession",profession);
        updateProfile.put("users/"+userId+"/institution",institution);
        final FirebaseAuth authenticator=FirebaseAuth.getInstance();
        AuthCredential credential= EmailAuthProvider.getCredential(email,oldPassword);
        System.out.println(email+": :"+newPassword+": :"+oldPassword);

        if(newPassword.equals(confirmPassword)){
            authenticator.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        System.out.println("task successfull");
                        FirebaseDatabase.getInstance().getReference().updateChildren(updateProfile);
                        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        authenticator.getCurrentUser().updateProfile(profileChangeRequest);
                        authenticator.getCurrentUser().updatePassword(newPassword);
                        Toast.makeText(context,"Update successful.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context,"Update Failed",Toast.LENGTH_SHORT).show();
                        System.out.println(" updation failed");
                    }
                    progressDialog.hide();
                }
            });
        }
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
