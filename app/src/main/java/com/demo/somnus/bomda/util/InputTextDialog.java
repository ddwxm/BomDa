package com.demo.somnus.bomda.util;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.demo.somnus.bomda.R;


/**
 * @author Jossing , Create on 2017/9/25
 * 输入Dialog工具类
 */

public class InputTextDialog extends AlertDialog implements OnClickListener {

    private TextInputLayout til;
    private TextInputEditText tiet;
    private Button btnOK;
    private Button btnCancel;

    private OnOkCallback callback;
    private boolean autoDismiss = true; //是否让 btnOK 自动 dismiss Dialog
    private int minCount = 0; //字数下限
    private int maxCount = Integer.MAX_VALUE; //字数上限

    public interface OnOkCallback {
        void onOk(String text);
    }

    public InputTextDialog(Context context) {
        this(context, "");
    }

    public InputTextDialog(Context context, CharSequence title) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_text, null, false);
        setView(view);
        setTitle(title);
        initWidget(view);
    }

    private void initWidget(View view) {
        til = (TextInputLayout) view.findViewById(R.id.til);
        til.setHintEnabled(true);
        tiet = (TextInputEditText) view.findViewById(R.id.tiet);
        btnOK = (Button) view.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(this);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                if (checkText()) {
                    if (autoDismiss) {
                        dismiss();
                    }
                    if (callback != null) {
                        callback.onOk(tiet.getText().toString().trim());
                    }
                }
                break;
        }
    }

    public void setHint(CharSequence hint) {
        if (hint != null && !TextUtils.isEmpty(hint)) {
            til.setHint(hint);
            til.setHintEnabled(true);
        } else {
            til.setHint("");
            til.setHintEnabled(false);
        }
    }

    public void defaultText(CharSequence text) {
        tiet.setText(text);
    }

    public void setOnOkCallback(boolean autoDismiss, OnOkCallback callback) {
        this.autoDismiss = autoDismiss;
        this.callback = callback;
    }

    public void setOnOkCallback(OnOkCallback callback) {
        this.callback = callback;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setSingleLine() {
        tiet.setLines(1);
        tiet.setSingleLine(true);
        tiet.setHorizontallyScrolling(true);
        tiet.setVerticalScrollBarEnabled(false);
        tiet.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
    }

    public void setMultiLine(int minLines, int maxLines) {
        tiet.setSingleLine(false);
        tiet.setMinLines(minLines);
        tiet.setMaxLines(maxLines);
        tiet.setHorizontallyScrolling(false);
        tiet.setVerticalScrollBarEnabled(true);
        tiet.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
    }

    private boolean checkText() {
        boolean result = true;
        String text = tiet.getText().toString().trim();
        tiet.setText(text); //去除首尾空格后再设置回去
        if (text.length() < minCount || text.length() > maxCount) {
            if (text.length() == 0) {
                til.setError("不能为空");
            } else if (maxCount == Integer.MAX_VALUE) {
                til.setError("字数应大于 " + minCount);
            } else {
                til.setError("字数应在 " + minCount + " 至 " + maxCount + "之间");
            }
            result = false;
        } else {
            til.setErrorEnabled(false);
        }
        return result;
    }
}
