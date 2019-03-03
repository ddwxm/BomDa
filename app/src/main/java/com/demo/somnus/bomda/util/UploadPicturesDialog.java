package com.demo.somnus.bomda.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.demo.somnus.bomda.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * @author Jossing , Create on 2017/9/24
 * 上传照片Dialog工具类
 */

public class UploadPicturesDialog extends AlertDialog implements UploadBatchListener {

    private TextView tvCurIndex;
    private TextView tvCurPercent;
    private TextView tvTotalPercent;
    private ProgressBar pbCur;
    private ProgressBar pbTotal;
    private LinearLayout llUploading;
    private LinearLayout llFailure;
    private TextView tvErrorMsg;
    private Button btnRetry;

    private String strCurIndex;
    private String strCurPercent;
    private String strTotalPercent;

    private String[] picturePaths; // 所有照片的路径

    private int successCount = 0; //统计上传成功的数量
    private boolean uploading = false;
    private UploadPicturesDoneCallback callback;


    /**
     * 当上传照片完成时回调这个接口
     */
    public interface UploadPicturesDoneCallback {
        void onUploadPicturesDone(List<BmobFile> files, BmobException e);
    }

    private UploadPicturesDialog(@NonNull Context context, CharSequence title) {
        super(context);
        strCurIndex = context.getString(R.string.dialog_upload_cur_index);
        strCurPercent = context.getString(R.string.dialog_upload_cur_percent);
        strTotalPercent = context.getString(R.string.dialog_upload_total_percent);
        setTitle(title);
        setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_upload, null, false);
        setView(view);
        initWidget(view);
    }

    public static UploadPicturesDialog create(@NonNull Context context) {
        return create(context, "上传文件");
    }

    public static UploadPicturesDialog create(@NonNull Context context, CharSequence title) {
        return new UploadPicturesDialog(context, title);
    }

    private void initWidget(View view) {
        llUploading = (LinearLayout) view.findViewById(R.id.ll_uploading);
        llFailure = (LinearLayout) view.findViewById(R.id.ll_failure);
        tvErrorMsg = (TextView) view.findViewById(R.id.tv_error_msg);
        btnRetry = (Button) view.findViewById(R.id.btn_retry);
        tvCurIndex = (TextView) view.findViewById(R.id.tv_cur_index);
        tvCurPercent = (TextView) view.findViewById(R.id.tv_cur_percent);
        tvTotalPercent = (TextView) view.findViewById(R.id.tv_total_percent);
        pbCur = (ProgressBar) view.findViewById(R.id.pb_cur);
        pbTotal = (ProgressBar) view.findViewById(R.id.pb_total);
    }

    public void startUpload(String picturePath, UploadPicturesDoneCallback callback) {
        List<String> paths = new ArrayList<>();
        paths.add(picturePath);
        startUpload(paths, callback);
    }

    public void startUpload(List<String> picturePathList, UploadPicturesDoneCallback callback) {
        if (isUploading()) {
            Toast.makeText(getContext(), "正在上传，请稍后", Toast.LENGTH_SHORT).show();
            return;
        }
        this.callback = callback;
        //构造照片路径数组
        picturePaths = new String[picturePathList.size()];
        picturePathList.toArray(picturePaths);
        //显示信息到 dialog
        tvCurIndex.setText(String.format(strCurIndex, 0));
        tvCurPercent.setText(String.format(strCurPercent, 0));
        tvTotalPercent.setText(String.format(strTotalPercent, 0, picturePaths.length));
        pbTotal.setMax(picturePaths.length);
        //显示 dialog
        super.show();
        successCount = 0;
        uploading = true;
        BmobFile.uploadBatch(picturePaths, this);
    }

    /**
     * @param curIndex     当前上传的文件下标
     * @param curPercent   当前上传文件的上传进度
     * @param total        总上传数
     * @param totalPercent 总进度
     */
    @Override
    public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
        tvCurIndex.setText(String.format(strCurIndex, curIndex));
        tvCurPercent.setText(String.format(strCurPercent, curPercent));
        tvTotalPercent.setText(String.format(strTotalPercent, curIndex, total));
        pbCur.setProgress(curPercent);
        pbTotal.setProgress(curIndex);
    }

    /**
     * @param files BmobFile 数组
     * @param urls 上传成功的文件在服务器的地址
     */
    @Override
    public void onSuccess(List<BmobFile> files, List<String> urls) {
        successCount += 1;
        if (successCount == picturePaths.length) { //成功次数和总数相等，上传成功
            notifyUploadingDone();
            callback.onUploadPicturesDone(files, null);
        }
    }

    @Override
    public void onError(final int statusCode, final String errorMsg) {
        llUploading.setVisibility(View.GONE);
        llFailure.setVisibility(View.VISIBLE);
        String errorMsgFormat = String.format(Locale.getDefault(),
                getContext().getString(R.string.dialog_upload_error_msg),
                successCount,
                statusCode,
                errorMsg);
        tvErrorMsg.setText(errorMsgFormat);
        //Log.e(TAG, "upload failure, ("+statusCode+") "+errorMsg);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyUploadingDone();
                callback.onUploadPicturesDone(null, new BmobException(statusCode, errorMsg));
            }
        });
    }

    public boolean isUploading() {
        return uploading;
    }

    private void notifyUploadingDone() {
        super.dismiss();
        successCount = 0; //重置
        uploading = false; //重置
    }

    @Override
    @Deprecated
    public void show() {}

    @Override
    @Deprecated
    public void dismiss() {}
}
