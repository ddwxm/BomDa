package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.News;
import com.demo.somnus.bomda.model.bean.Treasure;
import com.demo.somnus.bomda.util.StringUtil;
import com.demo.somnus.bomda.view.activity.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/5/7.
 * 跳骚市场适配器
 */

public class MarketAdapter extends RecyclerView.Adapter{

    private RecyclerView recyclerView;
    private List<Treasure> treasureList = new ArrayList<>();
    private Context context;

    public MarketAdapter(RecyclerView recyclerView, Context context){
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public void setMarketList(List<Treasure> treasureList){
        this.treasureList = new ArrayList<>(treasureList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_market_grid, parent, false);
        return new MarketItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MarketItemViewHolder) {
            Treasure treasure = treasureList.get(position);
            ((MarketItemViewHolder) holder).setMarket(treasure);
        }
    }

    @Override
    public int getItemCount() {
        return treasureList.size();
    }

    public class MarketItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_market_grid_type,item_market_grid_pic;
        TextView item_market_grid_name,item_market_grid_description,item_market_grid_label,item_market_grid_price;
        TextView item_market_grid_comment_num,item_market_grid_praise_num;
        private Treasure treasure;

        public MarketItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            item_market_grid_type = (ImageView) view.findViewById(R.id.item_market_grid_type);
            item_market_grid_pic = (ImageView) view.findViewById(R.id.item_market_grid_pic);
            item_market_grid_name = (TextView) view.findViewById(R.id.item_market_grid_name);
            item_market_grid_description = (TextView) view.findViewById(R.id.item_market_grid_description);
            item_market_grid_label = (TextView) view.findViewById(R.id.item_market_grid_label);
            item_market_grid_price = (TextView) view.findViewById(R.id.item_market_grid_price);
            item_market_grid_comment_num = (TextView) view.findViewById(R.id.item_market_grid_comment_num);
            item_market_grid_praise_num = (TextView) view.findViewById(R.id.item_market_grid_praise_num);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,treasure.getMarket().getName(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setMarket(Treasure treasure) {
            this.treasure = treasure;
            // 缩略图
            BmobFile image = treasure.getMarket().getPic1();
            if (image != null){
                Glide.with(context.getApplicationContext())
                        .load(image.getFileUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(item_market_grid_pic);
            } else {
                Glide.with(context.getApplicationContext())
                        .load(R.drawable.ic_placeholder)
                        .into(item_market_grid_pic);
            }
            // 加载名字
            item_market_grid_name.setText(treasure.getMarket().getName());
            // 加载描述
            item_market_grid_description.setText(treasure.getMarket().getDescription());
            // 加载Type
            if (treasure.getMarket().isType()){
                Glide.with(context.getApplicationContext())
                        .load(R.mipmap.goods_cs)
                        .into(item_market_grid_type);
            } else {
                Glide.with(context.getApplicationContext())
                        .load(R.mipmap.goods_qg)
                        .into(item_market_grid_type);
            }
            // 加载标签
            if (treasure.getMarket().getLabel() != null){
                item_market_grid_label.setText(treasure.getMarket().getLabel());
            } else {
                item_market_grid_label.setVisibility(View.GONE);
            }
            // 加载评论数量,好评
            item_market_grid_comment_num.setText(String.valueOf(treasure.getSum()));
            item_market_grid_praise_num.setText(StringUtil.percentage(treasure.getPraise(),treasure.getSum()));
            // 加载价格
            if (treasure.getMarket().getPrice() != null){
                item_market_grid_price.setText(treasure.getMarket().getPrice());
            } else {
                item_market_grid_price.setVisibility(View.GONE);
            }
        }

        public Treasure getTreasure() {
            return treasure;
        }
    }
}
