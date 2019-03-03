package com.demo.somnus.bomda;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.demo.somnus.bomda.util.SharedPreferencesUtils;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by Somnus on 2018/4/6.
 */

public class BomDaApplication extends Application {
    // 设备屏幕缩放级别
    public static float density;
    private static Toast toast;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        density = getResources().getDisplayMetrics().density;

        // 百度地图定位初始化
        SDKInitializer.initialize(getApplicationContext());

        // Bmob初始化
        bmobInitialize();

        // 储存工具初始化
        SharedPreferencesUtils.init(this);

    }

    /**
     * 初始化 Bmob SDK
     */
    private void bmobInitialize() {
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId("839eeaacb5f9ce7367591dddf4cf39e6")// 设置 appKey
                .setConnectTimeout(15)// 请求超时时间（单.位为秒）：默认 15s
                .setUploadBlockSize(1024*1024)// 文件分片上传时每片的大小（单位字节），默认512*1024
                .setFileExpiration(2500)// 文件的过期时间(单位为秒)：默认 1800s
                .build();
        Bmob.initialize(config);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 弹出一条 全局 Toast
     * @param msg Toast 内容
     * @param duration Toast 持续时间
     */
    public static void toastShow(Context context, String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, duration);
        } else {
            toast.setText(msg);
            toast.setDuration(duration);
        }
        toast.show();
    }

    /**
     * 把文件路径转换为 content uri 以适配 Android N
     * @param file 文件对象
     */
    public static Uri getContentUri(Context context, File file) {
        return FileProvider.getUriForFile(context, "com.xiaoming.bomda.fileprovider", file);
    }

    /**
     * 检查网络连接是否可用
     * @return true 表示可用，false 表示不可用
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
    }

}
