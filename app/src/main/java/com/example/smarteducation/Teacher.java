package com.example.smarteducation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class Teacher extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;

    private RecyclerView offirestore_list;
    private FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        firebaseFirestore = FirebaseFirestore.getInstance();
        offirestore_list = findViewById(R.id.firestore_list);
        //Query
        Query query = firebaseFirestore.collection("faculty");
        //recycler option
        FirestoreRecyclerOptions<ProductModel> options = new FirestoreRecyclerOptions.Builder<ProductModel>()
                .setQuery(query, ProductModel.class).build();

         adapter = new FirestoreRecyclerAdapter<ProductModel, ProductViewHolder>(options) {
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull ProductModel productModel) {
                productViewHolder.list_name.setText(productModel.getfName());
                productViewHolder.list_phone.setText(productModel.getPhone());
                productViewHolder.list_dp.setText(productModel.getdepartment());
            }
        };

              offirestore_list.setHasFixedSize(true);
              offirestore_list.setLayoutManager(new LinearLayoutManager(this));
              offirestore_list.setAdapter(adapter);
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

         private TextView list_name;
         private  TextView list_phone;
         private TextView list_dp;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name=itemView.findViewById(R.id.list_n);

            list_phone=itemView.findViewById(R.id.list_ph);
            list_dp=itemView.findViewById(R.id.list_dt);


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



