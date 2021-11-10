package com.example.quizapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.ProgramingModel;
import com.example.quizapp.R;

import java.util.ArrayList;

public class ProgramingAdapter extends RecyclerView.Adapter<ProgramingAdapter.viewHolder>{
  ArrayList<ProgramingModel>list;
  Context context;

    public ProgramingAdapter(ArrayList<ProgramingModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_recyclerview,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ProgramingModel model  = list.get(position);
        viewHolder.imageView.setImageResource(model.getPics());
        viewHolder.textView.setText(model.getText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("StaticFieldLeak")
        static
        ImageView imageView;
         @SuppressLint("StaticFieldLeak")
         static TextView textView;
        @SuppressLint("CutPasteId")
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textview);
        }

    }
}
