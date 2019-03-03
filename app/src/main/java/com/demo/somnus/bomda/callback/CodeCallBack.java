package com.demo.somnus.bomda.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by Somnus on 2018/4/5.
 * 验证码回调抽象类
 */

public abstract class CodeCallBack extends Callback<Bitmap> {
    @Override
    public Bitmap parseNetworkResponse(Response response) throws Exception {
        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
        response.body().close();
        return bitmap;
    }
}
