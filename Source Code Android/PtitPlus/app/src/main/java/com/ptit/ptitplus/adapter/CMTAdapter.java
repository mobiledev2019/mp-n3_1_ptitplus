package com.ptit.ptitplus.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptit.ptitplus.R;
import com.ptit.ptitplus.model.CMT;

import java.util.ArrayList;

public class CMTAdapter extends RecyclerView.Adapter<CMTAdapter.ViewHolder> {
    ArrayList<CMT> listCMT;
    Context context;

    public CMTAdapter(ArrayList<CMT> listCMT, Context context) {
        this.listCMT = listCMT;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_comment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindTo(listCMT.get(i));
    }

    @Override
    public int getItemCount() {
        return listCMT.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMsv;
        TextView textViewCmt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMsv = itemView.findViewById(R.id.textViewMSV);
            textViewCmt = itemView.findViewById(R.id.textViewComment);
        }
        public void bindTo(CMT cmtCurrent){
            textViewCmt.setText(cmtCurrent.getCmt());
            textViewMsv.setText(cmtCurrent.getMsv());
        }
    }
}
