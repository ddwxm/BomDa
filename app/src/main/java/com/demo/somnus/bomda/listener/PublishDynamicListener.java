package com.demo.somnus.bomda.listener;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.demo.somnus.bomda.util.ProgressDialogUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Somnus on 2018/4/12.
 * 发布动态监听
 */

public class PublishDynamicListener extends SaveListener<String> {

    private static final String TAG = "PublishDynamicListener";

    private Context context;
    private PublishDoneCallback callback;
    private ProgressDialogUtil progressDialog;

    /**
     * 当发布完成时回调这个接口
     */
    public interface PublishDoneCallback {
        void onPublishDone(BmobException e);
    }

    private PublishDynamicListener(Context context) {
        this.context = context;
        if (context instanceof PublishDoneCallback) {
            callback = (PublishDoneCallback) context;
        }
        progressDialog = new ProgressDialogUtil(context);
        progressDialog.setIngState("正在发布···");
        progressDialog.show();
    }

    public static PublishDynamicListener create(@NonNull Context context) {
        return new PublishDynamicListener(context);
    }

    @Override
    public void done(String str, BmobException e) {
        if (e == null) {
            progressDialog.setSuccessState("发布成功");
            progressDialog.dismissDelay(new ProgressDialogUtil.OnDelayDismissCallback() {
                @Override
                public void onDismiss() {
                    if (callback != null) {
                        callback.onPublishDone(null);
                    } else {
                        ((Activity) context).finish();
                    }
                }
            });
        } else {
            final int i = e.getErrorCode();
            final String s = e.getLocalizedMessage();
            progressDialog.setFailureState("发布失败", i, s);
            progressDialog.setActionBtn("好的", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.dismiss();
                    callback.onPublishDone(new BmobException(i, s));
                }
            });
        }
    }
}
