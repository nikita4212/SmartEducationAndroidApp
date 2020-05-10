package com.example.smarteducation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Books extends AppCompatActivity {
    private EditText year,department,class1,division,batch,subject,unit_no;
    private Button submitbutton;
    private TextView year1,department1,div1,batch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        //year=findViewById(R.id.editYear);
        //department=findViewById(R.id.editDepartment);
        //class1=findViewById(R.id.editClass);
        //division=findViewById(R.id.editDivision);
        // batch=findViewById(R.id.editBatch);
        subject=findViewById(R.id.subject);
        //unit_no=findViewById(R.id.unit_no);
        //assign_no=findViewById(R.id.editAssingment);
        submitbutton=findViewById(R.id.submitbutton);

        year1=findViewById(R.id.textView133);
        department1=findViewById(R.id.textView144);
        div1=findViewById(R.id.textView155);
        batch1=findViewById(R.id.textView166);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user= firebaseAuth.getCurrentUser();
        String userKey=user.getUid();


        final DocumentReference documentReference = db.collection("users").document(userKey);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                year1.setText(documentSnapshot.getString("class"));
                department1.setText(documentSnapshot.getString("department"));
                div1.setText(documentSnapshot.getString("division"));
                batch1.setText(documentSnapshot.getString("batch"));
            }
        });



    }

    public void onClick(View view){
        if (view == submitbutton){
            //String temp=getIntent().getStringExtra("path");
            String temp=year1.getText().toString()+"/"+department1.getText().toString()+"/"+"Notes/";
          /*  if(temp.endsWith("Assignment/")) {
                unit_no.setVisibility(View.INVISIBLE);
            }*/
            String path=temp+(subject.getText().toString())+("/");
            Intent intent=new Intent(getApplicationContext(),Approve.class);
            intent.putExtra("path", path);
            startActivity(intent);

            //finish();
        }
    }

}
