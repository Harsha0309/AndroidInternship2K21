package com.example.appbarwithoptionsmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //res >> to access into java file R.java  used
        //res >> to access into xml file @symbol  used
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Toast.makeText(this,"Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.name:
                Toast.makeText(this, "Name", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contact:
                Toast.makeText(this, "Contact", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}