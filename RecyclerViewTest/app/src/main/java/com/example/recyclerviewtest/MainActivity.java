package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ItemAdapter.MyInterface {
    RecyclerView rv;
    int totalAmount=0;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recyclerview);
        button = findViewById(R.id.button);
        int[] images ={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.a,R.drawable.a,R.drawable.d,R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};
        String[] itemnames = {"chicken65","mutton curry","Fish curry","prance","panipuri","Mutton Biryani","chicken noodles","veg noodles","veg pallav","nonveg pallav"};
        String[] itemprices = {"200","250","550","300","65","90","150","250","50","65"};
        rv.setLayoutManager(new LinearLayoutManager(this));
        ItemAdapter adapter = new ItemAdapter(this,images,itemnames,itemprices,this);
        rv.setAdapter(adapter);
    }

    public void submit(View view) {
    }

    @Override
    public void selectedItems(String itemName, String itemPrice, int position, int totalPrice) {
        Toast.makeText(this, ""+totalPrice,
                Toast.LENGTH_SHORT).show();
        Log.i("Total",""+totalPrice);
        totalAmount = totalAmount+Integer.parseInt(itemPrice);
        button.setText(String.valueOf(totalAmount));

    }
}