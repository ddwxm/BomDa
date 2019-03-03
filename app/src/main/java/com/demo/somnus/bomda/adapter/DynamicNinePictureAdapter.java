package com.demo.somnus.bomda.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.util.PictureParcelableUtil;
import com.demo.somnus.bomda.view.activity.PreviewPicturesActivity;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2018/4/12.
 * 动态九宫格图片适配器
 */

public class DynamicNinePictureAdapter extends NineGridImageViewAdapter<BmobFile> {

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, BmobFile bmobFile) {
        Glide.with(context.getApplicationContext())
                .load(bmobFile.getFileUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder_error)
                .into(imageView);
    }

    @Override
    protected ImageView generateImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.ic_placeholder);
        return imageView;
    }

    @Override
    protected void onItemImageClick(Context context, ImageView imageView,
                                    int index, List<BmobFile> pictures) {

        //ToastUtil.showShort(context, "点击了图片 " + index);
        Intent intent = new Intent(context, PreviewPicturesActivity.class);
        ArrayList<PictureParcelableUtil> pictureParcelables = getPictureParcelables(pictures);
        intent.putExtra(PreviewPicturesActivity.EXTRA_CURRENT_INDEX, index);
        intent.putParcelableArrayListExtra(PreviewPicturesActivity.EXTRA_PICTURES, pictureParcelables);
        if (context instanceof Activity) {
            context.startActivity(intent, ActivityOptions
                    .makeSceneTransitionAnimation((Activity)context).toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    private ArrayList<PictureParcelableUtil> getPictureParcelables(List<BmobFile> pictures) {
        ArrayList<PictureParcelableUtil> pictureParcelables = new ArrayList<>();
        for (BmobFile picture : pictures) {
            pictureParcelables.add(new PictureParcelableUtil(picture));
        }
        return pictureParcelables;
    }
}

