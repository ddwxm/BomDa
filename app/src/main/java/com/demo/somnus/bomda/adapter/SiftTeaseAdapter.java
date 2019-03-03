package com.demo.somnus.bomda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.TeaseSpecific;
import com.demo.somnus.bomda.util.ClickableSpanUtil;
import com.demo.somnus.bomda.util.DateTimeUtil;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Somnus on 2018/4/12.
 * 精选树洞适配器
 */

public class SiftTeaseAdapter extends RecyclerView.Adapter {
    private RecyclerView recyclerView;
    private List<TeaseSpecific> teaseList = new ArrayList<>();
    private Context context;

    public SiftTeaseAdapter(RecyclerView recyclerView, Context context){
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void setTeaseList(List<TeaseSpecific> teaseList) {
        this.teaseList = new ArrayList<>(teaseList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_tease, parent, false);
        return new TeaseItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TeaseItemViewHolder) {
            TeaseSpecific tease = teaseList.get(position);
            ((TeaseItemViewHolder) holder).setTease(tease);
        }
    }

    @Override
    public int getItemCount() {
        return teaseList.size();
    }

    public class TeaseItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_tease_avatar,item_tease_sex;
        TextView item_tease_nick,item_tease_location,item_tease_praise_num,item_tease_collection_num;
        TextView item_tease_time,item_tease_content,item_tease_comment_num;
        private RelativeLayout item_tease_location_rl;
        private NineGridImageView item_tease_pictures;
        private ShineButton item_tease_collection_sb,item_tease_praise_sb,item_tease_comment_sb;
        private TeaseSpecific tease;
        private String beforeText = "";

        public TeaseItemViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view){
            item_tease_avatar = (ImageView) view.findViewById(R.id.item_tease_avatar);
            item_tease_sex = (ImageView) view.findViewById(R.id.item_tease_sex);
            item_tease_nick = (TextView) view.findViewById(R.id.item_tease_nick);
            item_tease_time = (TextView) view.findViewById(R.id.item_tease_time);
            item_tease_location = (TextView) view.findViewById(R.id.item_tease_location);
            item_tease_content = (TextView) view.findViewById(R.id.item_tease_content);
            item_tease_location_rl = (RelativeLayout) view.findViewById(R.id.item_tease_location_rl);
            item_tease_praise_num = (TextView) view.findViewById(R.id.item_tease_praise_num);
            item_tease_collection_num = (TextView) view.findViewById(R.id.item_tease_collection_num);
            item_tease_comment_num = (TextView) view.findViewById(R.id.item_tease_comment_num);
            item_tease_pictures = (NineGridImageView) view.findViewById(R.id.item_tease_pictures);
            item_tease_collection_sb = (ShineButton) view.findViewById(R.id.item_tease_collection_sb);
            item_tease_praise_sb = (ShineButton) view.findViewById(R.id.item_tease_praise_sb);
            item_tease_comment_sb = (ShineButton) view.findViewById(R.id.item_tease_comment_sb);
            item_tease_pictures.setAdapter(new DynamicNinePictureAdapter());
            item_tease_praise_sb.setChecked(true);
            item_tease_collection_sb.setChecked(true);
            item_tease_comment_sb.setChecked(true);
            item_tease_praise_sb.setClickable(false);
            item_tease_praise_sb.setEnabled(false);
            item_tease_collection_sb.setClickable(false);
            item_tease_collection_sb.setEnabled(false);
            item_tease_comment_sb.setClickable(false);
            item_tease_comment_sb.setEnabled(false);
        }

        public void setTease(TeaseSpecific tease) {
            this.tease = tease;
            // 头像
            BmobFile avatar = tease.getTease().getAuthor().getAvatar();
            if (avatar != null){
                Glide.with(context.getApplicationContext())
                        .load(avatar.getFileUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar_24dp)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(item_tease_avatar);
            } else {
                Glide.with(context.getApplicationContext())
                        .load(R.drawable.ic_avatar_24dp)
                        .into(item_tease_avatar);
            }
            // 加载昵称
            item_tease_nick.setText(tease.getTease().getAuthor().getNick());
            // 加载时间
            item_tease_time.setText(DateTimeUtil.compare(tease.getTease().getCreatedAt()));
            // 加载内容以及Type
            switch (tease.getTease().getType()){
                case "正常":
                    item_tease_content.setText(tease.getTease().getContent());
                    break;
                default:
                    if (tease.getTease().getSift()){
                        beforeText = "#精选#"+" "+"#"+tease.getTease().getType()+"# ";
                    } else {
                        beforeText = "#"+tease.getTease().getType()+"# ";
                    }
                    SpannableString spannableString = new SpannableString(beforeText);
                    ClickableSpanUtil clickSpan = new ClickableSpanUtil(context, beforeText);
                    spannableString.setSpan(clickSpan, 0, beforeText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    item_tease_content.setText(spannableString);
                    item_tease_content.append(tease.getTease().getContent());
                    item_tease_content.setMovementMethod(LinkMovementMethod.getInstance());
                    break;
            }
            // 加载性别
            String sex = tease.getTease().getAuthor().getSex();
            if (sex != null){
                switch (sex){
                    case "男":
                        item_tease_sex.setImageResource(R.drawable.ic_man_blue_24dp);
                        break;
                    case "女":
                        item_tease_sex.setImageResource(R.drawable.ic_woman_pink_24dp);
                        break;
                }
            } else {
                item_tease_sex.setVisibility(View.GONE);
            }
            // 加载地点
            if (tease.getTease().getAddress() != null){
                item_tease_location.setText(tease.getTease().getAddress());
            } else {
                item_tease_location_rl.setVisibility(View.GONE);
            }
            // 加载喜欢、收藏、评论数量
            item_tease_praise_num.setText(String.valueOf(tease.getPraise()));
            item_tease_collection_num.setText(String.valueOf(tease.getCollection()));
            item_tease_comment_num.setText(String.valueOf(tease.getComment()));
            // 加载图片
            item_tease_pictures.setImagesData(tease.getTease().getPictures());
        }

        public TeaseSpecific getTease() {
            return tease;
        }
    }
}
