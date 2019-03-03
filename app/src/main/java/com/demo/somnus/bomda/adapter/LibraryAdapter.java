package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.Library;
import com.demo.somnus.bomda.model.bean.News;
import com.demo.somnus.bomda.view.activity.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2018/4/15.
 * 图书馆新闻RecyclerView适配器
 */

public class LibraryAdapter extends RecyclerView.Adapter {
    private RecyclerView recyclerView;
    private Context context;
    private List<Library> libraries = new ArrayList<>();

    public LibraryAdapter(RecyclerView recyclerView, Context context){
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void setNewsList(List<Library> libraries){
        this.libraries = new ArrayList<>(libraries);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_library, parent, false);
        return new LibraryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsAdapter.NewsItemViewHolder) {
            final Library library = libraries.get(position);
            ((LibraryItemViewHolder) holder).setNews(library);
        }
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    public class LibraryItemViewHolder extends RecyclerView.ViewHolder{
        TextView item_library_title;
        private Library library;

        public LibraryItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            item_library_title = (TextView) view.findViewById(R.id.item_library_title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NewsDetailsActivity.class);
                    intent.putExtra("href",library.getLink());
                    intent.putExtra("title",library.getTitle());
                    context.startActivity(intent);
                }
            });
        }

        public void setNews(final Library library){
            this.library = library;
            item_library_title.setText(library.getTitle());
        }
    }
}
