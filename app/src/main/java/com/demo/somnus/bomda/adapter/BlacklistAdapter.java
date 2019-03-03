package com.demo.somnus.bomda.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.Blacklist;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Somnus on 2018/1/17.
 * 黑名单Recyclerview适配器
 */

public class BlacklistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private RecyclerView rv;
    private List<Blacklist> blacklists = new ArrayList<>();

    public BlacklistAdapter(Activity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.rv = recyclerView;
    }

    public void setBlacklistList(List<Blacklist> blacklists) {
        this.blacklists = new ArrayList<>(blacklists);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_blacklist, parent, false);
        return new BlacklistsItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BlacklistsItemViewHolder) {
            Blacklist blacklist = blacklists.get(position);
            ((BlacklistsItemViewHolder) holder).setUser(blacklist);
        }
    }

    @Override
    public int getItemCount() {
        return blacklists.size();
    }

    public class BlacklistsItemViewHolder extends RecyclerView.ViewHolder {
        TextView nickname;
        ImageView avatar;
        Button remove;

        public BlacklistsItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }
        private void init(View view){
            nickname = (TextView) view.findViewById(R.id.item_blacklist_nickname);
            avatar = (ImageView) view.findViewById(R.id.item_blacklist_avatar);
            remove = (Button) view.findViewById(R.id.item_btn_remove);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(activity,blacklists.get(getPosition()).getObjectId());
                }
            });
        }

        public void setUser(Blacklist blacklist) {
            nickname.setText(blacklist.getObject().getNick());
            if(blacklist.getObject().getAvatar()!=null){
                Glide.with(activity.getApplicationContext())
                        .load(blacklist.getObject().getAvatar().getFileUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar_24dp)
                        .bitmapTransform(new CropCircleTransformation(activity))
                        .into(avatar);
            }
            else {
                avatar.setImageResource(R.drawable.ic_avatar_24dp);
            }
        }

        private void delete(final Context context, String objectId){
            final Blacklist blacklist = new Blacklist();
            blacklist.setObjectId(objectId);
            blacklist.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null){
                        Toast.makeText(context,"移除成功",Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        notifyItemRemoved(getPosition());
                    } else {
                        Toast.makeText(context,"移除失败"+e.getMessage()+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
