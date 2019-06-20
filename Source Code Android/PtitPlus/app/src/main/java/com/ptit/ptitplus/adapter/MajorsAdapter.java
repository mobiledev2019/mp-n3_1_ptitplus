package com.ptit.ptitplus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ptit.ptitplus.MajorsDetailActivity;
import com.ptit.ptitplus.R;
import com.ptit.ptitplus.model.Majors;

import java.util.ArrayList;

public class MajorsAdapter extends RecyclerView.Adapter<MajorsAdapter.ViewHolder> {
    ArrayList<Majors> listMajors;
    Context context;
    public MajorsAdapter(ArrayList<Majors> listMajors, Context context) {
        this.context = context;
        this.listMajors = listMajors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_majors, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Majors majorsCurrent = listMajors.get(i);
        viewHolder.bindTo(majorsCurrent);
    }

    @Override
    public int getItemCount() {
        return listMajors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.majorsImage);
            textView = itemView.findViewById(R.id.majorsName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MajorsDetailActivity.class);
                    String title = listMajors.get(getAdapterPosition()).getTenNganh();
                    intent.putExtra("title", title);
                    intent.putExtra("position", getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }

        public void bindTo(Majors majorsCurrent){
            Glide.with(context).load(majorsCurrent.getImageResource()).into(imageView);
            textView.setText(majorsCurrent.getTenNganh());
        }

    }
}
