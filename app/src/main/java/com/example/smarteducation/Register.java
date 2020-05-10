package com.example.smarteducation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword,mPhone,mCollege,mDepartment,mClass,mDivision,mBatch;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String status=null;
    String userId=null;
    FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        radioGroup   = findViewById(R.id.radioGroup);
        mFullName    = findViewById(R.id.fullName);
        mEmail       = findViewById(R.id.Email);
        mPassword    = findViewById(R.id.password);
        mPhone       = findViewById(R.id.phone);
        mCollege     = findViewById(R.id.collegename);
        mDepartment  = findViewById(R.id.DepartmentName);
        mClass       = findViewById(R.id.Class);
        mDivision    = findViewById(R.id.Division);
        mBatch       = findViewById(R.id.Batch);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn    = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null && fAuth.getCurrentUser().isEmailVerified()){

            progressBar.setVisibility(View.VISIBLE);

            fStore.collection("users").document(fAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.getResult().exists()){
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(getApplicationContext(),studenthomepage.class));
                        finish();
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(getApplicationContext(),teacherhomepage.class));
                        finish();
                    }
                }
            });

        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(radioButton.getText().equals("Student")) {
                    final String email = mEmail.getText().toString().trim();
                    final String password = mPassword.getText().toString().trim();
                    final String fullName = mFullName.getText().toString();
                    final String college = mCollege.getText().toString();
                    final String department = mDepartment.getText().toString();
                    final String classname = mClass.getText().toString();
                    final String division = mDivision.getText().toString();
                    final String batch = mBatch.getText().toString();
                    final String phone = mPhone.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        mEmail.setError("Email is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(college)) {
                        mCollege.setError("College is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(department)) {
                        mDepartment.setError("Department is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(classname)) {
                        mClass.setError("Class is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(division)) {
                        mDivision.setError("Division is Required.");
                        return;
                    }

                    if (TextUtils.isEmpty(batch)) {
                        mBatch.setError("Batch is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        mPassword.setError("Password is Required.");
                        return;
                    }

                    if (password.length() < 6) {
                        mPassword.setError("Password Must be >= 6 Characters");
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    // register the user in firebase

                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // send verification link

                                FirebaseUser fuser = fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Register.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                    }
                                });

                                Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("fName", fullName);
                                user.put("email", email);
                                user.put("password", password);
                                user.put("college", college);
                                user.put("department", department);
                                user.put("class", classname);
                                user.put("division", division);
                                user.put("batch", batch);
                                user.put("phone", phone);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();

                            } else {
                                Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }
                else
                {
                    final String email = mEmail.getText().toString().trim();
                    final String password = mPassword.getText().toString().trim();
                    final String fullName = mFullName.getText().toString();
                    final String college = mCollege.getText().toString();
                    final String department = mDepartment.getText().toString();
                    final String phone = mPhone.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        mEmail.setError("Email is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(college)) {
                        mCollege.setError("College is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(department)) {
                        mDepartment.setError("Department is Required.");
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        mPassword.setError("Password is Required.");
                        return;
                    }

                    if (password.length() < 6) {
                        mPassword.setError("Password Must be >= 6 Characters");
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    // register the user in firebase

                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // send verification link

                                FirebaseUser fuser = fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Register.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                    }
                                });

                                Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("faculty").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("fName", fullName);
                                user.put("email", email);
                                user.put("password", password);
                                user.put("college", college);
                                user.put("department", department);
                                user.put("phone", phone);
                                user.put("status","Not Approved");
                                user.put("id",userID);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: Faculty Profile is created for " + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();

                            } else {
                                Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });


                }


            }
        });



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        if(radioButton.getText().equals("Teacher")) {
            mClass.setVisibility(View.GONE);
            mDivision.setVisibility(View.GONE);
            mBatch.setVisibility(View.GONE);
        }
        else {
            mClass.setVisibility(View.VISIBLE);
            mBatch.setVisibility(View.VISIBLE);
            mDivision.setVisibility(View.VISIBLE);

        }

    }


}
