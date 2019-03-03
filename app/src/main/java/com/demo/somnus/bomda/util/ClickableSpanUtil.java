package com.demo.somnus.bomda.util;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Somnus on 2018/2/28.
 * 字符拼接工具类
 */

public class ClickableSpanUtil extends ClickableSpan {
    private Context context;
    private String text;

    public ClickableSpanUtil(Context context, String text)
    {
        this.context = context;
        this.text = text;
    }

    //在这里设置字体的大小，等各种属性
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#2196f3"));
    }

    @Override
    public void onClick(View widget) {
        //Intent intent = new Intent(MainActivity.this,OtherActivity.class);
        //startActivity(intent);
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}