package com.example.smarteducation;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final int flag=-1;

        SystemClock.sleep(1000);
        if(fAuth.getCurrentUser() != null && fAuth.getCurrentUser().isEmailVerified()){
            fStore.collection("users").document(fAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            startActivity(new Intent(getApplicationContext(), studenthomepage.class));
                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), teacherhomepage.class));
                            finish();
                        }
                }
            });
        }
    }
}