package com.example.quickchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void Mail(View view) {
        Intent i = new Intent(this, LoginMailActivity.class);
        startActivity(i);
    }

    public void Phone(View view) {
        Intent i = new Intent(this,PhoneActivity.class);
        startActivity(i);
    }
}