package com.example.smarteducation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AssignAdapter extends RecyclerView.Adapter<AssignAdapter.ViewHolder1> {
    RecyclerView recyclerView;
    Context context;
    ArrayList<String>urls=new ArrayList<>();

    ArrayList<String> items=new ArrayList<>();
    public void update(String name,String url){
        items.add(name);
        urls.add(url);
        notifyDataSetChanged();
    }

    public AssignAdapter(RecyclerView recyclerView, Context context,ArrayList<String> items,ArrayList<String> urls) {
        this.recyclerView = recyclerView;
        this.context=context;
        this.items=items;
        this.urls=urls;
    }

    public AssignAdapter(RecyclerView recView, Approve approve, ArrayList<String> strings, ArrayList<String> urls) {
        this(recView, (Context) approve, strings, urls);
    }



    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1= LayoutInflater.from(context).inflate(R.layout.my_rec_view,parent,false);
        return new ViewHolder1(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        
        holder.nameOfFiles.setText(items.get(position));

    }

    @Override
    public int getItemCount() {
        
        return items.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{
        TextView nameOfFiles;
        
        public  ViewHolder1(View itemview){
            super(itemview);
            nameOfFiles=itemview.findViewById(R.id.nameOfFiles);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=recyclerView.getChildAdapterPosition(view);
                    Intent intent=new Intent();
                    intent.setType(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urls.get(pos)));
                    context.startActivity(intent);
                }
            });
            
        }
    }
}
