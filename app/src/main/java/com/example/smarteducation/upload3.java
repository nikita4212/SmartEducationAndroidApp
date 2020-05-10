package com.example.smarteducation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class upload3 extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button buttonChoose3, buttonUpload3;
    private StorageReference mStorageRef;
    FirebaseDatabase mfirebase;
    DatabaseReference mref;
    private static final int PICK_IMAGE_REQUEST = 234;
    private String mypath;
    private EditText div3;
    private EditText  y,dep;
    private EditText filename;
    private String my,mdep,msub,mdiv,mbatch,mtype,mfname;
    private Spinner spinner3;
    private static final String[] path = {"FE", "SE", "TE", "BE"};
    Uri filePath;
    String str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload3);
        mfirebase=FirebaseDatabase.getInstance();

        //mref.setValue("Hi");
        spinner3 = (Spinner) findViewById(R.id.Year3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.year));
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) upload3.this);
        //imageView=(ImageView) findViewById(R.id.imageView);
        buttonChoose3 = (Button) findViewById(R.id.buttonChoose3);
        buttonUpload3 = (Button) findViewById(R.id.buttonUpload3);
        dep = findViewById(R.id.department3);
        mtype="Timetable";
        div3=findViewById(R.id.division3);
        filename = findViewById(R.id.filename);

        //HashMap<String,String> map=new HashMap<>();
       /* ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.department));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dep.setAdapter(adapter);
        dep.setOnItemSelectedListener(this);




        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Division));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        div.setAdapter(adapter);
        div.setOnItemSelectedListener(this);



        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.batch));
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batch.setAdapter(adapter);
        batch.setOnItemSelectedListener(this);


        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.type));
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(this);

*/


        //  str = dep.getText() +"/"+sub.getText()+"/"+div.getText()+"/"+batch.getText()+"/"+fname.getText();

        buttonChoose3.setOnClickListener((View.OnClickListener) this);
        buttonUpload3.setOnClickListener((View.OnClickListener) this);


        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select file"), PICK_IMAGE_REQUEST);
    }

    private void up() {
        Toast.makeText(getApplicationContext(),filename.getText().toString(),Toast.LENGTH_SHORT).show();
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            //map.put(assign_no.getText().toString(),fname.getText().toString());
            mypath=my+"/"+(dep.getText().toString())+"/"+((mtype)+"/"+(div3.getText().toString())+"/"+(filename.getText().toString()));
            // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
            //Toast.makeText(this,mypath,Toast.LENGTH_SHORT).show();
            mref=mfirebase.getReference(mypath);

            final StorageReference riversRef = mStorageRef.child(((my)+("/")+(dep.getText().toString())+("/")+(mtype)+("/")+ (div3.getText().toString())+("/")+(filename.getText().toString())+(".pdf")));
            //StorageReference riversRef = mStorageRef.child((new StringBuilder().append(str).toString()));
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            //  Uri downloadUrl = taskSnapshot.getStorage().getDownloadUrl().getResult();
                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String u=uri.toString();
                                    mref.setValue(u);
                                    Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });

                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @SuppressLint("ShowToast")
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG);
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Uploaded...");
                        }
                    });


        } else {
            //display toast
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            /*
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }


    public void onClick(View view) {
        // mdep=dep.getText().toString().trim();
        //msub=sub.getText().toString().trim();
        //mtype=type.getText().toString().trim();
        //mfname=fname.getText().toString().trim();
        //mbatch=batch.getText().toString().trim();
        //mdiv=div.getText().toString().trim();
        if (view == buttonChoose3) {
            //open file chooser
            showFileChooser();
        } else if (view == buttonUpload3) {
            up();
            //upload file to storage
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        my=adapterView.getSelectedItem().toString();
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}



