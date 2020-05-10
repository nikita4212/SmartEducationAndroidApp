package com.example.smarteducation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Approve extends AppCompatActivity {
    RecyclerView recView;
    FirebaseStorage  firebaseStorage;
    FirebaseDatabase mfirebaseref;
    DatabaseReference ref1;
    StorageReference mStorageRef;
    StorageReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        recView=findViewById(R.id.recView);
        String str= getIntent().getStringExtra("path");

        //
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(str);
        // xtra recView.setLayoutManager(new LinearLayoutManager(Approve.this));
        //xtra AssignAdapter assignAdapter=new AssignAdapter(recView,Approve.this,new ArrayList<String>(),new ArrayList<String>());
        //xtra recView.setAdapter(assignAdapter);
        //download();
        //ref1=mfirebaseref.getInstance().getReference();
       // mStorageRef=firebaseStorage.getInstance().getReference();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String filename=dataSnapshot.getKey();
                String url=dataSnapshot.getValue(String.class);

                ((AssignAdapter)recView.getAdapter()).update(filename,url);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recView.setLayoutManager(new LinearLayoutManager(Approve.this));
        AssignAdapter myadapter=new AssignAdapter(recView,Approve.this,new ArrayList<String>(),new ArrayList<String>());
        recView.setAdapter(myadapter);

        }
    public void download()
    {
        mStorageRef=firebaseStorage.getInstance().getReference();

    }
}
