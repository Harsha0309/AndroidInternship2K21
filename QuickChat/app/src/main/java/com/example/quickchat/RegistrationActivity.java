package com.example.quickchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {
    CircleImageView profile_image;
    EditText reg_name, reg_email, reg_pass, reg_confirm;
    FirebaseAuth auth;
    Uri imageuri;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String Imageuri;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        reg_name = findViewById(R.id.reg_name);
        profile_image = findViewById(R.id.profile_image);
        reg_email = findViewById(R.id.reg_email);
        reg_pass = findViewById(R.id.reg_pass);
        reg_confirm = findViewById(R.id.reg_confirm);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
            }
        });
    }

    public void signin(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginMailActivity.class));
    }

    public void signup(View view) {
        progressDialog.show();
        String name = reg_name.getText().toString();
        String email = reg_email.getText().toString();
        String password = reg_pass.getText().toString();
        String cpassword = reg_confirm.getText().toString();
        String status = "Hey there I'm using this application";
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(cpassword)) {
            progressDialog.dismiss();
            Toast.makeText(this, "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            progressDialog.dismiss();
            reg_email.setError("Invalid Email pattern");
            Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            progressDialog.dismiss();
            Toast.makeText(this, "Enter 8  character password", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(cpassword)) {
            progressDialog.dismiss();
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
        }
        else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
                        StorageReference storageReference = storage.getReference("upload").child(auth.getUid());
                        if (imageuri != null) {
                            storageReference.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Imageuri = uri.toString();
                                                Users users = new Users(auth.getUid(), name, email, Imageuri,status);
                                                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.dismiss();
                                                            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                                                        } else {
                                                            Toast.makeText(RegistrationActivity.this, "Error in creating a user", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            String status = "Hey there I'm using this application";
                            Imageuri = "https://firebasestorage.googleapis.com/v0/b/quickchat-5ec02.appspot.com/o/profilelogo.jpg?alt=media&token=796f3fd2-3d61-4e35-a51b-60f75ea149e2";
                            Users users = new Users(auth.getUid(), name, email, Imageuri,status);
                            reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                                    } else {
                                        Toast.makeText(RegistrationActivity.this, "Error in creating a  New user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==10){
            if (data!=null){
                imageuri = data.getData();
                profile_image.setImageURI(imageuri);
            }
        }
    }
}