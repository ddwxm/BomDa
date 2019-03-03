package com.demo.somnus.bomda.adapter;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.demo.somnus.bomda.BomDaApplication;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.util.ToastUtil;
import com.demo.somnus.bomda.view.activity.PostTeaseActivity;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Somnus on 2018/4/12.
 * 图片上传适配器
 */

public class PictureUploadAdapter extends RecyclerView.Adapter<PictureUploadAdapter.PictureVH> {
    private static final String TAG = "PictureUploadAdapter";

    private PostTeaseActivity activity;
    private float density;
    private View uploadBtn;

    private List<String> picturePaths = null;
    private final int MAX_SIZE = 9;
    private int realPictureSize; //实际大小，不包括 null

    public PictureUploadAdapter(@NonNull Activity activity, @Nullable List<String> picturePaths) {
        if (activity instanceof PostTeaseActivity) {
            this.activity = (PostTeaseActivity) activity;
            density = activity.getResources().getDisplayMetrics().density;
            if (picturePaths == null) {
                this.picturePaths = new ArrayList<>();
            } else {
                this.picturePaths = new ArrayList<>(picturePaths);
            }
            realPictureSize = this.picturePaths.size();
            this.picturePaths.add(null);
        }
    }

    public ArrayList<String> getRealPicturePaths() {
        return new ArrayList<>(picturePaths.subList(0, realPictureSize));
    }

    /**
     * 恢复上传图片的按钮可点击，然后清空全局变量
     */
    public void enableUploadBtn() {
        if (this.uploadBtn == null) {
            Log.e(TAG, "enableUploadBtn: 状态异常！");
            return;
        }
        this.uploadBtn.setClickable(true);
        this.uploadBtn = null;
    }

    /**
     * 把上传图片的按钮设为不可点击，并保存到全局变量
     * @param uploadBtn 上传图片的按钮
     */
    private void disableUploadBtn(@NonNull View uploadBtn) {
        if (this.uploadBtn != null) {
            Log.e(TAG, "disableUploadBtn: 状态异常！");
            return;
        }
        uploadBtn.setClickable(false);
        this.uploadBtn = uploadBtn;
    }

    /**
     * 增加一个图片
     * @param path 目标图片的路径
     */
    public void addItem(String path) {
        int pictureOldSize = picturePaths.size();
        if (pictureOldSize < MAX_SIZE) {
            picturePaths.add(pictureOldSize - 1, path);
            realPictureSize += 1;
            notifyItemInserted(pictureOldSize - 1);
        } else if (picturePaths.size() == MAX_SIZE) {
            picturePaths.set(MAX_SIZE-1, path);
            realPictureSize += 1;
            notifyItemChanged(MAX_SIZE-1);
        }
        //Log.e(TAG, "picture size: "+pictures.size());
    }

    /**
     * 移除一个图片 URI
     * @param position 目标 URI 在数组中的位置
     */
    public void removeItem(int position) {
        int pictureOldSize = picturePaths.size();
        if (pictureOldSize == MAX_SIZE && picturePaths.get(MAX_SIZE-1) != null) {
            picturePaths.remove(position);
            realPictureSize -= 1;
            notifyItemRemoved(position);
            picturePaths.add(null);
            notifyItemInserted(MAX_SIZE-1);
        } else {
            picturePaths.remove(position);
            realPictureSize -= 1;
            notifyItemRemoved(position);
        }
    }

    @Override
    public PictureVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureVH(parent);
    }

    @Override
    public void onBindViewHolder(PictureVH holder, int position) {
        //Log.e(TAG, "bind position: "+position);
        //Log.e(TAG, "item count: "+getItemCount());
        if (picturePaths.get(position) == null) {
            holder.rlUpload.setVisibility(View.VISIBLE);
            holder.rlPicture.setVisibility(View.GONE);
        } else {
            holder.rlUpload.setVisibility(View.GONE);
            holder.rlPicture.setVisibility(View.VISIBLE);
            int radius = Math.round(4 * density); //4dp
            Glide.with(BomDaApplication.getContext())
                    .load(picturePaths.get(position))
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder_error)
                    .bitmapTransform(
                            new CenterCrop(activity),
                            new RoundedCornersTransformation(activity, radius, 0))
                    .into(holder.ivPicture);
        }
    }

    @Override
    public int getItemCount() {
        return picturePaths.size();
    }



    public class PictureVH extends RecyclerView.ViewHolder {

        RelativeLayout rlPicture;
        RelativeLayout rlUpload;
        ImageView ivPicture;

        PictureVH(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_publish_dynamic_picture, parent, false));

            rlPicture = (RelativeLayout) itemView.findViewById(R.id.rl_picture);
            rlUpload = (RelativeLayout) itemView.findViewById(R.id.rl_upload);
            rlUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disableUploadBtn(rlUpload);
                    activity.selectPictures(MAX_SIZE - realPictureSize);
                }
            });
            ivPicture = (ImageView) itemView.findViewById(R.id.iv_picture);
            ivPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtil.showShort(activity, "点击了图片");
                }
            });
            ivPicture.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    /* 震动 */
                    Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
                    vib.vibrate(120);
                    Snackbar snackbar = Snackbar
                            .make(activity.findViewById(R.id.cl_activity_publish_dynamic),
                                    "要删除图片" + (getAdapterPosition()+1) + "吗？",
                                    Snackbar.LENGTH_LONG);
                    snackbar.setAction("删除", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int adapterPosition = getAdapterPosition();
                            // 重复点击时会出点 -1 的情况，所以要判断一下
                            if (adapterPosition >= 0) {
                                removeItem(adapterPosition);
                            }
                        }
                    });
                    snackbar.show();
                    return true;
                }
            });
        }

    }
}

