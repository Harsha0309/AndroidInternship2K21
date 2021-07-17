package com.example.quickchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginMailActivity extends AppCompatActivity {
    EditText loginemail, loginpassword;
    TextView signin;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        auth = FirebaseAuth.getInstance();
        signin = findViewById(R.id.txtsignin);
        loginemail = findViewById(R.id.loginemail);
        loginpassword = findViewById(R.id.loginpassword);

    }

    public void signup(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    public void signin(View view) {
        progressDialog.show();

        String email = loginemail.getText().toString().trim();
        String password = loginpassword.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(this, "Enter Valid data", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            progressDialog.dismiss();
            loginemail.setError("Invalid email");
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            progressDialog.dismiss();
            loginpassword.setError("Minimum 8 characters");
            Toast.makeText(this, "Please enter valid Password", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginMailActivity.this, HomeActivity.class));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginMailActivity.this, "Error in login", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}