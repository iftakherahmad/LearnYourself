package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity4 extends Activity {
    private EditText emailInput;
    private EditText passwordInput;
    private ImageButton loginButton;
    private FirebaseAuth authenticator;
    private ProgressDialog progressDialog;
    private String catche=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login4);
        System.out.println("1");
        setTitle("LOG IN");
        System.out.println("3");
        emailInput=findViewById(R.id.email);
        passwordInput=findViewById(R.id.password);
        loginButton=findViewById(R.id.loginbutton);
        authenticator=FirebaseAuth.getInstance();
        System.out.println("2");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(LoginActivity4.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle("Authenticating ...");
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                String email=emailInput.getText().toString();
                String password=passwordInput.getText().toString();
                if(email.length()<6 || password.length()<6)
                    Toast.makeText(LoginActivity4.this,"Email and Password should have at least 6 characters.",Toast.LENGTH_SHORT).show();
                else {
                    if(catche!=null)Toast.makeText(LoginActivity4.this,"Authenticating please wait.",Toast.LENGTH_SHORT).show();
                    else login(email, password);
                }
            }
        });
        System.out.println("4");
    }
//    private void signup(){
//
//    }

    private void login(String email, String password) {
        catche="running";
        authenticator.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.hide();
                    Intent intent=new Intent(LoginActivity4.this,LoginActivity5.class);
                    Toast.makeText(LoginActivity4.this,"Login successful.",Toast.LENGTH_SHORT).show();
                    catche=null;
                    startActivity(intent);

                }
                else{
                    catche=null;
                    progressDialog.hide();
                    Toast.makeText(LoginActivity4.this,"Authentication failed.",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.hide();
                Toast.makeText(LoginActivity4.this,"Something went wrong check network connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signup(View view) {
        Intent intent=new Intent(this,SignUpActivity3.class);
        startActivity(intent);
    }
}
