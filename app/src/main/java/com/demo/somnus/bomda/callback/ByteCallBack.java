package com.demo.somnus.bomda.callback;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by Somnus on 2018/4/7.
 */

public abstract class ByteCallBack extends Callback<byte[]> {

    @Override
    public byte[] parseNetworkResponse(Response response) throws Exception {
        byte[] code = response.body().bytes();
        response.body().close();
        return code;
    }
}
