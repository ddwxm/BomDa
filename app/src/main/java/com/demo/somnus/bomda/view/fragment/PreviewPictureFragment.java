package com.demo.somnus.bomda.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.demo.somnus.bomda.R;

import cn.bmob.v3.datatype.BmobFile;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by Somnus on 2018/4/12.
 * 图片预览Fragment
 */

public class PreviewPictureFragment extends Fragment {

    private static final String TAG = "PreviewPictureFragment";

    private static final String ARG_PICTURE = "Picture";

    private BmobFile picture;

    private ImageViewTouch ivt;

    public static PreviewPictureFragment newInstance(@NonNull BmobFile picture) {
        PreviewPictureFragment fragment = new PreviewPictureFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PICTURE, picture);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null) return;
        picture = (BmobFile) args.getSerializable(ARG_PICTURE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview_picture, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivt = (ImageViewTouch) view.findViewById(R.id.ivt);
        ivt.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);

        if (picture == null) return;
        //Log.e(TAG, picture.getFileUrl());
        Glide.with(getContext().getApplicationContext())
                .load(picture.getFileUrl())
                .placeholder(R.drawable.ic_placeholder_no_bg)
                .error(R.drawable.ic_placeholder_error)
                .into(ivt);
    }

    public void resetView() {
        if (ivt != null) {
            ivt.resetMatrix();
        }
    }
}

