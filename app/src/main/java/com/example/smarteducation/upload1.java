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

public class upload1 extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemSelectedListener{
    private static final int PICK_IMAGE_REQUEST = 234;
    //private ImageView imageView;
    private Button buttonChoose1, buttonUpload1;
    private StorageReference mStorageRef;
    FirebaseDatabase mfirebase;
    DatabaseReference mref;
    private String mypath;
    private EditText fname;
    private EditText  y,dep,sub,div,batch,unit_no;
    private String my,mdep,msub,mdiv,mbatch,mtype,mfname;
    private Spinner spinner1;
    private static final String[] path = {"FE", "SE", "TE", "BE"};
    Uri filePath;
    String str="";
    //HashMap<String,String> map=new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload1);
        mfirebase=FirebaseDatabase.getInstance();

        //mref.setValue("Hi");
        spinner1 = (Spinner) findViewById(R.id.Year1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.year));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) upload1.this);
        //imageView=(ImageView) findViewById(R.id.imageView);
        buttonChoose1 = (Button) findViewById(R.id.buttonChoose1);
        buttonUpload1 = (Button) findViewById(R.id.buttonUpload1);
        dep = findViewById(R.id.department1);
        sub   = findViewById(R.id.subject1);

        mtype="Notes";
        fname=findViewById(R.id.fname1);
        div = findViewById(R.id.div1);
        unit_no=findViewById(R.id.unit_no);
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

        buttonChoose1.setOnClickListener((View.OnClickListener) this);
        buttonUpload1.setOnClickListener((View.OnClickListener) this);


        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select file"), PICK_IMAGE_REQUEST);
    }

    private void up() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            //map.put(assign_no.getText().toString(),fname.getText().toString());
            mypath=my+"/"+(dep.getText().toString())+"/"+(mtype)+"/"+(sub.getText().toString())+"/"+(unit_no.getText().toString())+(fname.getText().toString());
            // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
            //Toast.makeText(this,mypath,Toast.LENGTH_SHORT).show();
            mref=mfirebase.getReference(mypath);
            final StorageReference riversRef = mStorageRef.child(((my)+("/")+(dep.getText().toString())+("/")+(mtype)+("/")+(sub.getText()
                    .toString())+("/")+(unit_no.getText().toString())+("/")+(fname.getText().toString())+(".pdf")));
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
                                    Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG);
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
        if (view == buttonChoose1) {
            //open file chooser
            showFileChooser();
        } else if (view == buttonUpload1) {
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

