package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.Focus;
import com.demo.somnus.bomda.model.bean.News;
import com.demo.somnus.bomda.model.bean.Tease;
import com.demo.somnus.bomda.view.activity.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Somnus on 2018/4/15.
 * 关注适配器
 */

public class FocusAdapter extends RecyclerView.Adapter{

    private RecyclerView recyclerView;
    private Context context;
    private List<Focus> focusList = new ArrayList<>();

    public FocusAdapter(RecyclerView recyclerView, Context context){
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void setFocusList(List<Focus> focusList){
        this.focusList = new ArrayList<>(focusList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_focus, parent, false);
        return new FocusItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FocusItemViewHolder) {
            final Focus focus = focusList.get(position);
            ((FocusItemViewHolder) holder).setFocus(focus);
        }
    }

    @Override
    public int getItemCount() {
        return focusList.size();
    }

    public class FocusItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView item_focus_avatar;
        private TextView item_focus_nick,item_focus_signature;
        private Button item_focus_focus;
        private Focus focus;

        public FocusItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            item_focus_avatar = (ImageView) view.findViewById(R.id.item_focus_avatar);
            item_focus_nick = (TextView) view.findViewById(R.id.item_focus_nick);
            item_focus_signature = (TextView) view.findViewById(R.id.item_focus_signature);
            item_focus_focus = (Button) view.findViewById(R.id.item_focus_focus);
        }

        public void setFocus(final Focus focus){
            this.focus = focus;
            Log.e("adapter",focus.getUser().getObjectId());
            BmobFile avatar = focus.getUser().getAvatar();
            if (avatar != null){
                Glide.with(context.getApplicationContext())
                        .load(avatar.getFileUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar_24dp)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(item_focus_avatar);
            } else {
                Glide.with(context.getApplicationContext())
                        .load(R.drawable.ic_avatar_24dp)
                        .into(item_focus_avatar);
            }
            item_focus_nick.setText(focus.getUser().getNick());
            item_focus_signature.setText(focus.getUser().getSignature());
            item_focus_focus.setText(R.string.item_tease_isFocus);
        }
    }
}
