package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.News;
import com.demo.somnus.bomda.view.activity.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2018/2/22.
 * 校内新闻RecyclerView适配器
 */

public class NewsAdapter extends RecyclerView.Adapter {
    private RecyclerView recyclerView;
    private Context context;
    private List<News> newsList = new ArrayList<>();

    public NewsAdapter(RecyclerView recyclerView, Context context){
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void setNewsList(List<News> newsList){
        this.newsList = new ArrayList<>(newsList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_news, parent, false);
        return new NewsItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsItemViewHolder) {
            final News news = newsList.get(position);
            ((NewsItemViewHolder) holder).setNews(news);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder{
        TextView item_news_title,item_news_type,item_news_time;
        private News news;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            item_news_title = (TextView) view.findViewById(R.id.item_news_title);
            item_news_time = (TextView) view.findViewById(R.id.item_news_time);
            item_news_type = (TextView) view.findViewById(R.id.item_news_type);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NewsDetailsActivity.class);
                    intent.putExtra("href",news.getLink());
                    intent.putExtra("title",news.getTitle());
                    context.startActivity(intent);
                }
            });
        }

        public void setNews(final News news){
            this.news = news;
            item_news_title.setText(news.getTitle());
            item_news_type.setText(news.getType());
            item_news_time.setText(news.getDate());
        }
    }
}
