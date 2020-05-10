package com.example.smarteducation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class teacherhomepage extends AppCompatActivity {

    Button assupload,notesupload,noticeupload,timetableupload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherhomepage);


        assupload = findViewById(R.id.assupload);
        notesupload = findViewById(R.id.notesupload);
        noticeupload = findViewById(R.id.noticeupload);
        timetableupload = findViewById(R.id.timetableupload);



        assupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),upload.class));
            }
        });

        notesupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),upload1.class));
            }
        });

        noticeupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),upload2.class));
            }
        });

        timetableupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),upload3.class));
            }
        });


    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

}
