package com.ptit.ptitplus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.ptitplus.NewsContentActivity;
import com.ptit.ptitplus.model.News;
import com.ptit.ptitplus.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<News> mNewsData;
    private Context mContext;

    public NewsAdapter(ArrayList<News> mNewsData, Context mContext) {
        this.mNewsData = mNewsData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_news,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder viewHolder, int i) {
        News currentNews = mNewsData.get(i);
        viewHolder.bindTo(currentNews);
    }

    @Override
    public int getItemCount() {
        return mNewsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleText;
        private TextView mDateText;
        private ImageView mImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.title);
            mDateText = itemView.findViewById(R.id.time);
            mImage = itemView.findViewById(R.id.newsImage);
            itemView.setOnClickListener(this);
        }
        public void bindTo(News currentNews){
            mTitleText.setText(currentNews.getTitle());
            mDateText.setText(currentNews.getTime());
            Glide.with(mContext).load(currentNews.getImageResource()).apply(new RequestOptions().fitCenter()).into(mImage);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, NewsContentActivity.class);
            String link = mNewsData.get(getAdapterPosition()).getLink();
            intent.putExtra("link", link);
            mContext.startActivity(intent);
        }
    }
}
