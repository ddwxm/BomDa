package com.demo.somnus.bomda.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.demo.somnus.bomda.model.bean.Collection;
import com.demo.somnus.bomda.model.bean.Focus;
import com.demo.somnus.bomda.model.bean.InfoNum;
import com.demo.somnus.bomda.model.bean.RealTime;
import com.demo.somnus.bomda.model.bean.Tease;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.model.bean.WelcomePicture;
import com.demo.somnus.bomda.presenter.IPresenter.IWelcomePresenter;
import com.demo.somnus.bomda.util.BaiDuLbsUtil;
import com.demo.somnus.bomda.view.IView.IWelcomeActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Somnus on 2018/4/4.
 * 欢迎Presenter
 */

public class WelcomePresenter implements IWelcomePresenter {
    private final String api_key = "yPYYPgpi2CqP21DL/";
    private static String prefix = "https://api.caiyunapp.com/v2/";
    private static String comma = ",";
    private static String suffix = "/realtime.json";
    private IWelcomeActivity activity;
    private float radius;

    public WelcomePresenter(IWelcomeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showWelcomePicture(final Context context) {
        // 先尝试读取缓存
        String url = getWelcomePictureURLFromCache(context);
        if (!url.equals("null")) {
            activity.showWelcomePicture(url);
        }
        // 再从网络获取
        BmobQuery<WelcomePicture> query = new BmobQuery<>();
        query.setLimit(1);
        query.findObjects(new FindListener<WelcomePicture>() {
            @Override
            public void done(List<WelcomePicture> list, BmobException e) {
                if (e == null) {
                    WelcomePicture welcomePicture = list.get(0);
                    String url = welcomePicture.getPictureURL(context);
                    welcomePictureURLCache(context, url);
                    activity.showWelcomePicture(url);
                } else {
                    Log.e("获取图片失败", "(" + e.getErrorCode() + ")" + e.getMessage());
                    //Toast.makeText(context, e.getMessage() + "(" + e.getErrorCode() + ")", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 缓存欢迎图片的 URL 到 SharedPreferences
     * @param context Context 对象
     * @param url 图片的 URL
     */
    private void welcomePictureURLCache(Context context, String url) {
        SharedPreferences spf = context.getSharedPreferences("WelcomePictureURL", MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("URL", url);
        editor.apply();
    }

    /**
     * 从缓存读取图片 URL，如果没有就 return "null"
     * @param context Context 对象
     * @return 欢迎图片的 URL
     */
    private String getWelcomePictureURLFromCache(Context context) {
        SharedPreferences spf = context.getSharedPreferences("WelcomePictureURL", MODE_PRIVATE);
        return spf.getString("URL", "null");
    }

    @Override
    public void getWeather(final Context context) {
        BaiDuLbsUtil baiDuLbsUtil = new BaiDuLbsUtil();
        baiDuLbsUtil.getLocation(context, new BaiDuLbsUtil.Callback() {
            @Override
            public void onFinish(BDLocation location) {
                if (location!= null){
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    float direction = location.getDirection();// 手机方向信息
                    String city = location.getCity();
                    Log.w("city",city);
                    String str = location.getAddrStr();
                    Log.w("str",str);
                    String street = location.getStreet();
                    Log.w("street",street);
                    String district = location.getDistrict();
                    Log.w("district",district);
                    if (location.hasRadius()) {// 判断是否有定位精度半径
                        radius = location.getRadius();
                    }
                    activity.sendLocationInfo(latitude,longitude,district,city,street,str);
                    getWeather(latitude,longitude,context);
                } else {
                    activity.sendLocationInfo(0,0,"未知","未知","未知","未知");
                }
            }
        });
    }

    @Override
    public void getNum(final User user) {
        final InfoNum infoNum = new InfoNum();
        BmobQuery<Tease> teaseBmobQuery = new BmobQuery<>();
        teaseBmobQuery.addWhereEqualTo("author",user.getObjectId());
        teaseBmobQuery.findObjects(new FindListener<Tease>() {
            @Override
            public void done(List<Tease> list, BmobException e) {
                if (e == null){
                    infoNum.setDynamicNum(list.size());
                    BmobQuery<User> userBmobQuery = new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("objectId",user.getObjectId());
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null){
                                infoNum.setPraiseNum(list.get(0).getPraise());
                                BmobQuery<Collection> collectionBmobQuery = new BmobQuery<>();
                                collectionBmobQuery.addWhereEqualTo("author",user.getObjectId());
                                collectionBmobQuery.findObjects(new FindListener<Collection>() {
                                    @Override
                                    public void done(List<Collection> list, BmobException e) {
                                        if (e == null){
                                            infoNum.setCollectionNum(list.size());
                                            BmobQuery<Focus> focusBmobQuery = new BmobQuery<>();
                                            focusBmobQuery.addWhereEqualTo("focuser",user.getObjectId());
                                            focusBmobQuery.findObjects(new FindListener<Focus>() {
                                                @Override
                                                public void done(List<Focus> list, BmobException e) {
                                                    if (e == null){
                                                        infoNum.setFocusNum(list.size());
                                                        Log.e("num",infoNum.getDynamicNum()+
                                                                infoNum.getCollectionNum()+infoNum.getFocusNum()+
                                                                infoNum.getPraiseNum()+"");
                                                        activity.num(infoNum);
                                                    } else {

                                                    }
                                                }
                                            });
                                        } else{

                                        }
                                    }
                                });
                            } else {

                            }
                        }
                    });
                } else {

                }
            }
        });
    }

    private void getWeather(double latitude, double longitude, final Context context){
        final GetBuilder post = OkHttpUtils.get();
        post.url(url(latitude,longitude))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show();
                Log.e("error",e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                RealTime realTime = gson.fromJson(response,RealTime.class);
                activity.sendWeather(realTime.getResult().getTemperature(),realTime.getResult().getSkycon(),realTime.getServer_time()
                        ,realTime.getResult().getAqi());
            }
        });
    }

    private String url(double latitude, double longitude){
        Log.e("url",prefix+api_key+longitude+comma+latitude+suffix);
        return prefix+api_key+longitude+comma+latitude+suffix;
    }
}
