package com.example.quickchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    String ReceiverImage,ReceiverUID,ReceiverName,Senderuid;
    CircleImageView profileimage;
    TextView receivername;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    //static used to adapters
    public static String sImage;
    public static String rImage;

    CardView sendbtn;
    EditText edittextmessage;

    String senderRoom,receiverRoom;

    RecyclerView messageadapter;
    ArrayList<Messages> messagesArrayList;

    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ReceiverName = getIntent().getStringExtra("name");
        ReceiverImage = getIntent().getStringExtra("ReceiverImage");
        ReceiverUID = getIntent().getStringExtra("uid");

        messagesArrayList = new ArrayList<>();

        profileimage = findViewById(R.id.profile_image);
        receivername = findViewById(R.id.receiver_name);

        messageadapter = findViewById(R.id.messageadapter);

        messagesArrayList = new ArrayList<>();
        adapter = new MessageAdapter(ChatActivity.this,messagesArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //to start from new messages
        linearLayoutManager.setStackFromEnd(true);
        messageadapter.setLayoutManager(linearLayoutManager);
        messageadapter.setAdapter(adapter);
        sendbtn = findViewById(R.id.sendbtn);
        edittextmessage = findViewById(R.id.edittextmessage);

        Picasso.get().load(ReceiverImage).into(profileimage);
        receivername.setText(""+ReceiverName);
        Senderuid = firebaseAuth.getUid();

        senderRoom = Senderuid + ReceiverUID;
        receiverRoom = ReceiverUID + Senderuid;



        DatabaseReference reference = database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                sImage = snapshot.child("imageuri").getValue().toString();
                rImage = ReceiverImage;
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Messages messages = dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edittextmessage.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(ChatActivity.this, "Please enter valid message", Toast.LENGTH_SHORT).show();
                    return;
                }
                edittextmessage.setText("");
                Date date = new Date();

                Messages messages = new Messages(message,Senderuid,date.getTime());

                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull  Task<Void> task) {
                        database.getReference().child("chats")
                                .child(receiverRoom)
                                .child("messages")
                                .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull  Task<Void> task) {

                            }
                        });
                    }
                });

            }
        });

    }
}