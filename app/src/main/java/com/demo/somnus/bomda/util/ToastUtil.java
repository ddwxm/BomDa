package com.demo.somnus.bomda.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Jossing , Create on 2017/9/13
 * 消息提示工具类
 */

public final class ToastUtil {

    private static Toast toast;

    private ToastUtil() { }

    public static void showShort(Context context, CharSequence msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, CharSequence msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }

    private static void show(Context context, CharSequence msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
        } else {
            toast.setText(msg);
            toast.setDuration(duration);
        }
        toast.show();
    }
}
