package com.example.smarteducation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


public class adminpage extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;

    private Query db;
    Button approve;
    public String name;
    private RecyclerView offirestore_list;
    private FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        firebaseFirestore = FirebaseFirestore.getInstance();
        offirestore_list = findViewById(R.id.firestore_list1);
        //Query
        Query query = firebaseFirestore.collection("faculty").whereEqualTo("status","Not Approved");
        //recycler option
        FirestoreRecyclerOptions<ProductModel> options = new FirestoreRecyclerOptions.Builder<ProductModel>()
                .setQuery(query, ProductModel.class).build();

        adapter = new FirestoreRecyclerAdapter<ProductModel, adminpage.ProductViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull adminpage.ProductViewHolder productViewHolder, int i, @NonNull final ProductModel productModel) {
                productViewHolder.list_name.setText(productModel.getfName());
                productViewHolder.list_phone.setText(productModel.getPhone());
                productViewHolder.list_dp.setText(productModel.getdepartment());
                productViewHolder.list_st.setText(productModel.getId());


                productViewHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentReference docRef = firebaseFirestore.collection("faculty").document(productModel.getId());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("status","Approved");
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(adminpage.this, productModel.getfName()+" Approved", Toast.LENGTH_SHORT).show();
                                /// startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                        });

                    }
                });

            }

            @NonNull
            @Override
            public adminpage.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tadmin,parent,false);
                return new adminpage.ProductViewHolder(view);
            }

        };

        offirestore_list.setHasFixedSize(true);
        offirestore_list.setLayoutManager(new LinearLayoutManager(this));
        offirestore_list.setAdapter(adapter);
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private  TextView list_phone;
        private TextView list_dp;
        private  TextView list_st;
        Button btn;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name=itemView.findViewById(R.id.list_n);
            list_phone=itemView.findViewById(R.id.list_ph);
            list_dp=itemView.findViewById(R.id.list_dt);
            list_st = itemView.findViewById(R.id.list_st);


            btn = itemView.findViewById(R.id.approve);
        }

    }

    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
}



