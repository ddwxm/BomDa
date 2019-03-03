package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.Activities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somnus on 2018/4/4.
 * 活动Recyclerview适配器
 */

public class ActivityAdapter extends RecyclerView.Adapter {
    private RecyclerView recyclerView;
    private List<Activities> activitiesList = new ArrayList<>();
    private Context context;

    public ActivityAdapter(Context context, RecyclerView rv){
        this.context = context;
        this.recyclerView = rv;
    }

    public void setActivitiesList(List<Activities> activitiesList) {
        this.activitiesList = new ArrayList<>(activitiesList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_activity, parent, false);
        return new ActivitiesItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ActivitiesItemViewHolder) {
            Activities activities = activitiesList.get(position);
            ((ActivitiesItemViewHolder) holder).setActivities(activities);
        }
    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }

    public class ActivitiesItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_activity_name,item_activity_address,item_activity_info,item_activity_sponsor;
        TextView item_activity_time;
        private Activities activities;

        public ActivitiesItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            item_activity_name = (TextView) view.findViewById(R.id.item_activity_name);
            item_activity_address = (TextView) view.findViewById(R.id.item_activity_address);
            item_activity_info = (TextView) view.findViewById(R.id.item_activity_info);
            item_activity_sponsor = (TextView) view.findViewById(R.id.item_activity_sponsor);
            item_activity_time = (TextView) view.findViewById(R.id.item_activity_time);
        }

        public void setActivities(Activities activities) {
            this.activities = activities;
            item_activity_name.setText(activities.getName());
            item_activity_address.setText(activities.getAddress());
            item_activity_info.setText(activities.getInfo());
            item_activity_sponsor.setText(activities.getSponsor());
            item_activity_time.setText(activities.getTime());

        }

        public Activities getActivities() {
            return activities;
        }

    }
}

