package bd.univdhaka.cse2216.learnyorself;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URLConnection;

public class SignUpActivity3 extends Activity {
    private EditText nameInput;
    private EditText emailInput;
    private EditText institutionInput;
    private Spinner professionInput;
    private EditText passwordInput;
    private  EditText confirmPasswordInput;
    private ImageButton selectProfilePic;
    private Button confirmButton;
    private TextView selectedFile;
    private Uri imageUri;
    private String name="";
    private String email="";
    private String profession="";
    private String institution="";
    private String password="";
    private String confirmPassword="";
    private String fileExtention="";
    private String profilePicUrl="";
    private FileHandler fileHandler;
    Context context;
    FirebaseAuth authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        setTitle("SIGN UP");
        //find the fields
        nameInput=findViewById(R.id.name);
        context=SignUpActivity3.this;
        authenticator=FirebaseAuth.getInstance();
        emailInput=findViewById(R.id.email);
        institutionInput=findViewById(R.id.institution);
        professionInput=findViewById(R.id.profession);
        passwordInput=findViewById(R.id.password);
        confirmPasswordInput=findViewById(R.id.confirmpassword);
        selectProfilePic=findViewById(R.id.uploadimage);
        confirmButton=findViewById(R.id.confirm);
        selectedFile=findViewById(R.id.selectedfile);

        //select profile image
        selectProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(SignUpActivity3.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectImage();
                    if (imageUri != null) {// Upload the image if valid image is selected

                    }
                }
                else{
                    ActivityCompat.requestPermissions(SignUpActivity3.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},102);
                }
            }
        });
        //Cheack validity of user info and then create a new user
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUserIsInfoIsValid()) {


                    FirebaseHandler firebaseHandler=new FirebaseHandler(SignUpActivity3.this);

                    try {
                        if(fileHandler.getDowloadUrl()!=null) {
                            firebaseHandler.createNewUser(name, email, institution, profession, password, fileHandler.getDowloadUrl());
                        }

                        
                        else {
                            Toast.makeText(SignUpActivity3.this,"Please select a valid profile picture.",Toast.LENGTH_SHORT).show();
                        }
                       // Toast.makeText(SignUpActivity3.this,"User created successfully .",Toast.LENGTH_SHORT).show();
                    //    Intent intent1=new Intent(SignUpActivity3.this,LoginActivity5.class);
                     //   startActivity(intent1);
                    } catch (Exception e) {
                        Toast.makeText(SignUpActivity3.this,"User not created exception occoured",Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==102 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectImage();
        }
        else{
            Toast.makeText(SignUpActivity3.this,"Please give permission",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {

        Intent intent=new Intent();
        intent.setType("application/jpeg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,82);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==82 && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            selectedFile.setText("File:"+imageUri.toString().substring(imageUri.toString().lastIndexOf('/')));
            fileHandler = new FileHandler(SignUpActivity3.this);
            fileHandler.uploadPhoto(imageUri);
            profilePicUrl = fileHandler.getDowloadUrl();
            Toast.makeText(SignUpActivity3.this,profilePicUrl,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(SignUpActivity3.this,"Please select a file",Toast.LENGTH_SHORT).show();
        }
    }


    boolean checkUserIsInfoIsValid(){
         name=nameInput.getText().toString();
         email=emailInput.getText().toString();
         profession=professionInput.getSelectedItem().toString();
         institution =institutionInput.getText().toString();
         password=passwordInput.getText().toString();
         confirmPassword=confirmPasswordInput.getText().toString();
         if(imageUri!=null)
            fileExtention=imageUri.toString();
         if(fileExtention.contains(".")){
            fileExtention=fileExtention.substring(fileExtention.lastIndexOf('.')+1);
            if(!fileExtention.equals("jpg") || !fileExtention.equals("JPG")||!fileExtention.equals("PNG")||!fileExtention.equals("Png")){
                return false;
            }
            else {
                Toast.makeText(SignUpActivity3.this,"Invalid image png/jpg is prefered",Toast.LENGTH_SHORT).show();
            }
        }
        if(name.equals("")||email.equals("")||password.equals("")||confirmPassword.equals("")){
            Toast.makeText(SignUpActivity3.this,"Name ,Email,Password can not be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(confirmPassword)){
            Toast.makeText(SignUpActivity3.this,"Password confirmation invalid",Toast.LENGTH_SHORT).show();
            return false;
        }

            return true;
       }

}
