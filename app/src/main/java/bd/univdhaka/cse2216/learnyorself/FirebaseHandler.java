package bd.univdhaka.cse2216.learnyorself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirebaseHandler {
    FirebaseAuth authenticator;
    DatabaseReference database;
    Context context;
    FirebaseAuth.AuthStateListener listener;
    FirebaseHandler(Context context){
        authenticator=FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance().getReference();
        this.context=context;
    }

        public  boolean signIn(String email,String password){
            return authenticator.signInWithEmailAndPassword(email,password).isSuccessful();
        }

    public  void createNewUser(final String name, final String email, final String institution, final String profession, final String password, final String profilePicUrl) {
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Creating new user...");
        progressDialog.show();
        authenticator.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(context, "New user created successfully.", Toast.LENGTH_SHORT).show();
                    authenticator.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                authenticator.getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.hide();
                                            DatabaseReference userTable= FirebaseDatabase.getInstance().getReference("users");
                                            String uid;
                                            try {
                                                uid= authenticator.getCurrentUser().getUid();
                                            }
                                            catch (NullPointerException e){
                                                uid=userTable.push().getKey();
                                            }
                                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                            Date date = new Date();
                                            String time=formatter.format(date);
                                            User user=new User(name,profession,institution,profilePicUrl,time,email,uid,0,0,0);
                                            userTable.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Intent intent1=new Intent(context,LoginActivity5.class);
                                                    context.startActivity(intent1);
                                                }
                                            });

                                        }

                                    }
                                });

                            }
                            else{
                                progressDialog.hide();
                                Toast.makeText(context,"Sign in failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //  FirebaseAuth.AuthStateListener mAuthListener=new FirebaseAuth.AuthStateListener() {
                    //   @Override
                    //   public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {



                    //   }
                    //  };
                    //authenticator.addAuthStateListener(mAuthListener);




                }
                else{
                    progressDialog.hide();
                    Toast.makeText(context,"Account not created something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
