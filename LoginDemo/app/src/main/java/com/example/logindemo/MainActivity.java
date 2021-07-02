package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button login;
    private TextView info;
    private  int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText)findViewById(R.id.etname);
        Password =(EditText)findViewById(R.id.etpassword);
        login = (Button)findViewById(R.id.btlogin);
        info =(TextView)findViewById(R.id.tvinfo);
        info.setText("No of attempts remaining : 5");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

    }
    private void validate(String username,String userpassword){
        if ((username.equals("Admin")) && (userpassword.equals("1234"))){
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);
        }
        else{
            counter--;
            info.setText("Number of attempts remaining : " +String.valueOf(counter));
            if (counter == 0){
                login.setEnabled(false);
            }
        }
    }
}