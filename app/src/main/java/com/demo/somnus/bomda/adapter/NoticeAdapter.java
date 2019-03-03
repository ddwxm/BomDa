package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.Announcement;
import com.demo.somnus.bomda.view.activity.NoticeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2018/1/23.
 * 校园公告Recyclerview适配器
 */

public class NoticeAdapter extends RecyclerView.Adapter {
    private RecyclerView recyclerView;
    private Context context;
    private List<Announcement> announcementList = new ArrayList<>();

    public NoticeAdapter(RecyclerView recyclerView, Context context){
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void setAnnouncementList(List<Announcement> announcementList){
        this.announcementList = new ArrayList<>(announcementList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_notice, parent, false);
        return new NoticeItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoticeItemViewHolder) {
            final Announcement announcement = announcementList.get(position);
            ((NoticeItemViewHolder) holder).setAnnouncement(announcement);
        }
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public class NoticeItemViewHolder extends RecyclerView.ViewHolder{
        TextView announcement_title,item_announcement_type,item_announcement_time;
        private Announcement announcement;

        public NoticeItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            announcement_title = (TextView) view.findViewById(R.id.item_announcement_title);
            item_announcement_time = (TextView) view.findViewById(R.id.item_announcement_time);
            item_announcement_type = (TextView) view.findViewById(R.id.item_announcement_type);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NoticeDetailsActivity.class);
                    intent.putExtra("href",announcement.getHref());
                    intent.putExtra("title",announcement.getTitle());
                    context.startActivity(intent);
                }
            });
        }

        public void setAnnouncement(final Announcement announcement){
            this.announcement = announcement;
            announcement_title.setText(announcement.getTitle());
            item_announcement_type.setText(announcement.getType());
            item_announcement_time.setText(announcement.getTime());
        }
    }
}
