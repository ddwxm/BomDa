package com.demo.somnus.bomda.callback;


import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by Somnus on 2018/4/5.
 * 网页源码回调抽象类
 */

public abstract class InfoCallBack extends Callback<String> {

    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        String result = response.body().string();
        response.body().close();
        return result;
    }
}
