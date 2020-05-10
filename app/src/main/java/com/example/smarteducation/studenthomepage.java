package com.example.smarteducation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class studenthomepage extends AppCompatActivity {

    GridView gridView;
    private TextView year1,department1,div1,batch1;
    String[] numberword = {"Profile","Books","Teachers","Timetable","Notice","Assignment"};
    int[] numberimage = {R.drawable.profile,R.drawable.book,R.drawable.teacher,R.drawable.timetable,R.drawable.notice,R.drawable.assignment};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studenthomepage);



        year1=findViewById(R.id.textView133);
        department1=findViewById(R.id.textView144);
        div1=findViewById(R.id.textView155);
        batch1=findViewById(R.id.textView166);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user= firebaseAuth.getCurrentUser();
        String userKey=user.getUid();

        if (user != null) {

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



        gridView = findViewById(R.id.grid_view);

        MainAdapter adapter = new MainAdapter(studenthomepage.this,numberword,numberimage);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(),"You clicked "+numberword[+position],Toast.LENGTH_SHORT).show();
                if( numberword[+position].equals("Teachers")){
                    startActivity(new Intent(getApplicationContext(),Teacher.class));
                }
                else if(numberword[+position].equals("Profile")){
                    startActivity(new Intent(getApplicationContext(),studentprofile.class));
                }
                else if(numberword[+position].equals("Books")){
                    startActivity(new Intent(getApplicationContext(),Books.class));
                }
                else if(numberword[+position].equals("Timetable")){

                    String str1=year1.getText().toString()+"/"+department1.getText().toString()+"/"+"Timetable"+"/"+div1.getText().toString();
                    Intent intent=new Intent(getApplicationContext(),Approve.class);
                    intent.putExtra("path",str1);
                    startActivity(intent);

                    //startActivity(new Intent(getApplicationContext(),Timetable.class));
                }
                else if(numberword[+position].equals("Notice")){


                    String str=year1.getText().toString()+"/"+department1.getText().toString()+"/"+"Notice/";
                    Intent intent=new Intent(getApplicationContext(),Approve.class);
                    intent.putExtra("path",str);
                    startActivity(intent);



                  //  startActivity(new Intent(getApplicationContext(),Notice.class));
                }
                else if(numberword[+position].equals("Assignment"))
                {
                    startActivity(new Intent(getApplicationContext(),Assignment.class));
                }
            }
        });

    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

}
