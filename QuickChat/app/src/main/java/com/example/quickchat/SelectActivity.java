package com.example.quickchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Mail(View view) {
        Intent i = new Intent(this,MailActivity.class);
        startActivity(i);
    }

    public void Phone(View view) {
        Intent i = new Intent(this,PhoneActivity.class);
        startActivity(i);
    }
}