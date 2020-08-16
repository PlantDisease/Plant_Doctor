package com.example.plant;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{

    public interface OnClickListener{
        void onClick(model item);
    }

    private OnClickListener onClickListener;
    private List<model> data;


    public Myadapter(List<model> data, OnClickListener onClickListener) {
        this.data = data;
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final model model = data.get(position);
        holder.h1.setImageResource(model.getImg());
        holder.h2.setText(model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView h1;
        TextView h2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            h1 = (ImageView)itemView.findViewById(R.id.h1);
            h2 = (TextView)itemView.findViewById(R.id.h2);
        }
    }
}
