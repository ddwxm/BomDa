package com.demo.somnus.bomda.util;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.demo.somnus.bomda.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Somnus on 2018/1/18.
 * 进度提示框工具类
 */

public class ProgressDialogUtil extends AlertDialog {

    private ImageView ivSuccess;
    private ImageView ivFailure;
    private ProgressBar pb;
    private TextView tvMsg;
    private TextView tvErrorMsg;
    private LinearLayout llActionBar;
    private Button btn;

    private static final int STATE_ING = 61;
    private static final int STATE_SUCCESS = 862;
    private static final int STATE_FAILURE = 893;

    private static final int DEFAULT_ERROR_CODE = -1;
    private static final int DEFAULT_DELAY_MS = 1000;

    private int curState = STATE_ING;

    public ProgressDialogUtil(@NonNull Context context) {
        super(context);
        setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false);
        setView(view);
        initWidget(view);
    }

    private void initWidget(View v) {
        ivFailure = (ImageView) v.findViewById(R.id.iv_failure);
        ivSuccess = (ImageView) v.findViewById(R.id.iv_success);
        pb = (ProgressBar) v.findViewById(R.id.pb);
        tvMsg = (TextView) v.findViewById(R.id.tv_msg);
        tvErrorMsg = (TextView) v.findViewById(R.id.tv_error_msg);
        llActionBar = (LinearLayout) v.findViewById(R.id.ll_action_bar);
        btn = (Button) v.findViewById(R.id.btn);
    }

    /**
     * 设置为正在执行状态
     * @param msg 提示消息
     */
    public void setIngState(CharSequence msg) {
        curState = STATE_ING;
        pb.setVisibility(View.VISIBLE);
        ivSuccess.setVisibility(View.GONE);
        ivFailure.setVisibility(View.GONE);
        disableErrorMsg();
        disableActionBtn();
        setMsg(msg);
    }

    /**
     * 设置为执行成功状态
     * @param msg 提示消息
     */
    public void setSuccessState(CharSequence msg) {
        curState = STATE_SUCCESS;
        pb.setVisibility(View.GONE);
        ivSuccess.setVisibility(View.VISIBLE);
        ivFailure.setVisibility(View.GONE);
        disableErrorMsg();
        disableActionBtn();
        setMsg(msg);
    }

    /**
     * 设置为执行失败状态
     * @param msg 提示消息
     * @param errorMsg 错误消息
     */
    public void setFailureState(CharSequence msg, CharSequence errorMsg) {
        setFailureState(msg, DEFAULT_ERROR_CODE, errorMsg);
    }

    /**
     * 设置为执行失败状态
     * @param msg 提示消息
     * @param code 错误代码
     * @param errorMsg 错误消息
     */
    public void setFailureState(CharSequence msg, int code, CharSequence errorMsg) {
        curState = STATE_FAILURE;
        pb.setVisibility(View.GONE);
        ivSuccess.setVisibility(View.GONE);
        ivFailure.setVisibility(View.VISIBLE);
        disableActionBtn();
        setMsg(msg);
        setErrorMsg(code, errorMsg);
    }

    /**
     * 设置提示消息
     * @param msg 提示消息
     */
    public void setMsg(CharSequence msg) {
        tvMsg.setText(msg);
    }

    /**
     * 使用默认的错误代码来设置错误消息
     * 当状态不是 STATE_FAILURE 时无效
     * @see #STATE_FAILURE
     * @see #DEFAULT_ERROR_CODE
     * @param errorMsg 错误消息
     */
    public void setErrorMsg(CharSequence errorMsg) {
        setErrorMsg(DEFAULT_ERROR_CODE, errorMsg);
    }

    /**
     * 设置错误消息
     * 当状态不是 STATE_FAILURE 时无效
     * @see #STATE_FAILURE
     * @param code 错误代码
     * @param errorMsg 错误消息
     */
    public void setErrorMsg(int code, CharSequence errorMsg) {
        if (curState == STATE_FAILURE) {
            String errorMsgFormat = getContext().getResources()
                    .getString(R.string.dialog_progress_error_msg, code, errorMsg);
            tvErrorMsg.setText(errorMsgFormat);
            tvErrorMsg.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 禁用错误消息
     */
    public void disableErrorMsg() {
        tvErrorMsg.setText("");
        tvErrorMsg.setVisibility(View.GONE);
    }

    /**
     * 设置动作按钮上的文本和点击监听器
     * @param text 按钮文本
     * @param listener 点击监听器
     */
    public void setActionBtn(CharSequence text, View.OnClickListener listener) {
        btn.setText(text);
        btn.setOnClickListener(listener);
        llActionBar.setVisibility(View.VISIBLE);
    }

    /**
     * 禁用 dialog 上的动作按钮
     */
    public void disableActionBtn() {
        btn.setText("");
        btn.setOnClickListener(null);
        llActionBar.setVisibility(View.GONE);
    }

    /**
     * 延时 dismiss dialog
     * @param ms 延迟的毫秒数
     * @param callback 延时结束 dismiss 时的回调
     */
    public void dismissDelay(long ms, @Nullable final OnDelayDismissCallback callback) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                dismiss();
                if (callback != null) {
                    callback.onDismiss();
                }
            }
        }, ms);
    }

    /**
     * 使用默认的延时来 dismiss dialog
     * @see #DEFAULT_DELAY_MS
     * @param callback 延时结束 dismiss 时的回调
     */
    public void dismissDelay(@Nullable final OnDelayDismissCallback callback) {
        dismissDelay(DEFAULT_DELAY_MS, callback);
    }

    /**
     * 延时 dismiss dialog 的回调
     */
    public interface OnDelayDismissCallback {
        void onDismiss();
    }

    @Override
    @Deprecated
    public void setMessage(CharSequence message) {}

    @Override
    @Deprecated
    public void setTitle(CharSequence title) {}

    @Override
    @Deprecated
    public void setTitle(int titleId) {}

    @Override
    @Deprecated
    public void setButton(int whichButton, CharSequence text, Message msg) {}

    @Override
    @Deprecated
    public void setButton(int whichButton, CharSequence text, OnClickListener listener) {}
}

