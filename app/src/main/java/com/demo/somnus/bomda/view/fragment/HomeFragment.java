package com.demo.somnus.bomda.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.ActivityAdapter;
import com.demo.somnus.bomda.callback.ByteCallBack;
import com.demo.somnus.bomda.model.bean.Activities;
import com.demo.somnus.bomda.model.bean.Homing;
import com.demo.somnus.bomda.model.bean.Shuffling;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.HomePresenter;
import com.demo.somnus.bomda.presenter.IPresenter.IHomePresenter;
import com.demo.somnus.bomda.util.AppBarStateUtil;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.util.SnackBarUtil;
import com.demo.somnus.bomda.util.WeatherUtil;
import com.demo.somnus.bomda.view.IView.IHomeFragment;
import com.demo.somnus.bomda.view.activity.BindingActivity;
import com.demo.somnus.bomda.view.activity.CalendarActivity;
import com.demo.somnus.bomda.view.activity.GradeActivity;
import com.demo.somnus.bomda.view.activity.LibraryActivity;
import com.demo.somnus.bomda.view.activity.MainActivity;
import com.demo.somnus.bomda.view.activity.NewsActivity;
import com.demo.somnus.bomda.view.activity.NoticeActivity;
import com.demo.somnus.bomda.view.activity.ScheduleActivity;
import com.demo.somnus.bomda.view.activity.TestActivity;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.youth.banner.Banner;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import okhttp3.Call;

/**
 * Created by Somnus on 2018/4/4.
 * 首页Fragment
 */

public class HomeFragment extends Fragment implements IHomeFragment
        ,View.OnClickListener{
    private IHomePresenter iHomePresenter;
    private View rootView;
    private LinearLayout home_search_txt;
    private ImageView home_search_scan,weather_skyIcon_icon,weather_aqi_icon;
    private Banner fragment_home_banner;
    private TextView function_announcement,function_calendar,function_news,function_library;
    private TextView function_schedule,function_grade,function_times,function_service;
    private RecyclerView home_homing_body;
    private RecyclerView home_activity_body;
    private ActivityAdapter activityAdapter;
    private List<Shuffling> shufflingList = new ArrayList<>();
    private List<String> url = new ArrayList<>();
    private CardView fragment_home_tips;
    private String[] images;
    private Toolbar toolbar;
    private AppBarLayout mAppBarLayout;
    private LinearLayout title;
    private CoordinatorLayout home_CoordinatorLayout;
    private RelativeLayout fragment_home_search;
    private String district,locationdescribe,temperature,skycon;
    private TextView weather_update_time,weather_temperature,weather_skyIcon,weather_aqi_description,weather_location;
    private TextView weather_prompt_content;
    private long server_time;
    private double aqi;
    private byte[] VerificationCode;

    String[] image= new String[] {
            "http://bmob-cdn-16034.b0.upaiyun.com/2018/01/20/67965b9040d00da480caf3afa7e2dbb2.jpg",
            "http://bmob-cdn-16034.b0.upaiyun.com/2018/01/20/5747a2804025e7418005cf760f9c664e.jpg",
            "http://bmob-cdn-16034.b0.upaiyun.com/2018/01/20/ca768fc940018c9b801b28793ec20050.jpg",
            "http://bmob-cdn-16034.b0.upaiyun.com/2018/01/20/864570f140b3f61c80c27abb16b09dff.jpg",
            "http://bmob-cdn-16034.b0.upaiyun.com/2018/01/24/57bab10340c7b0e38074831b41208c02.gif"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        iHomePresenter = new HomePresenter(this);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        iHomePresenter.search_shuffling(getActivity());
        iHomePresenter.search_activity(getActivity());
        initWidget(rootView);
        initClick();
        //getCode();
        return rootView;
    }

    /**
     * 获取传递的天气数据
     * @param district
     * @param temperature
     * @param skycon
     */
    public void getTemperature(String district, String temperature, String skycon,long server_time,double aqi){
        this.district = district;
        this.temperature = temperature;
        this.skycon = skycon;
        this.server_time = server_time;
        this.aqi = aqi;
    }

    private void getCode(){
        OkHttpUtils.get()
                .url("http://210.37.0.16/CheckCode.aspx")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                .addHeader("Cookie","ASP.NET_SessionId=5opk3c45cdnrcx55khwluh3j")
                .build().execute(new ByteCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("Exception",e.getMessage());
            }

            @Override
            public void onResponse(byte[] response) {
                Log.e("byte","cg");
                VerificationCode = response;
            }
        });
    }

    /**
     * 控件初始化
     * @param view
     */
    private void initWidget(View view){
        toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        toolbar.getBackground().setAlpha(0);//toolbar透明度初始化为0
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        fragment_home_search = (RelativeLayout) view.findViewById(R.id.fragment_home_search);
        home_CoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.home_CoordinatorLayout);

        mAppBarLayout= (AppBarLayout) view.findViewById(R.id.AppFragment_AppBarLayout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateUtil() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int i) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    toolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.colorWhite),Math.abs(i*1.0f)/appBarLayout.getTotalScrollRange()));
                    fragment_home_search.setBackground(getResources().getDrawable(R.drawable.home_search_bg));
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    toolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.colorWhite),Math.abs(i*1.0f)/appBarLayout.getTotalScrollRange()));
                    fragment_home_search.setBackground(getResources().getDrawable(R.drawable.life_search_bg));
                }else {
                    //中间状态
                }
            }
        });

        fragment_home_tips = (CardView) view.findViewById(R.id.fragment_home_tips);
        fragment_home_tips.setVisibility(View.GONE);

        home_search_txt = (LinearLayout) view.findViewById(R.id.home_search_txt);

        home_search_scan = (ImageView) view.findViewById(R.id.home_search_scan);

        fragment_home_banner = (Banner) view.findViewById(R.id.fragment_home_banner);
        fragment_home_banner.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);
        fragment_home_banner.setIndicatorGravity(Banner.CENTER);
        fragment_home_banner.setDelayTime(3000);//banner.setImages(imgs);
        //自定义图片加载框架
        fragment_home_banner.setImages(image, new Banner.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                System.out.println("加载中");
                //Glide.with(getContext()).load(url).into(view);
                Glide.with(getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
                System.out.println("加载完");
            }
        });

        //设置点击事件，下标是从1开始
        fragment_home_banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                //设置点击事件
                Toast.makeText(getContext(), shufflingList.get(position-1).getOnClick(), Toast.LENGTH_SHORT).show();
            }
        });

        function_announcement = (TextView) view.findViewById(R.id.function_announcement);
        function_calendar = (TextView) view.findViewById(R.id.function_calendar);
        function_news = (TextView) view.findViewById(R.id.function_news);
        function_library = (TextView) view.findViewById(R.id.function_library);
        function_schedule = (TextView) view.findViewById(R.id.function_schedule);
        function_grade = (TextView) view.findViewById(R.id.function_grade);
        function_times = (TextView) view.findViewById(R.id.function_times);
        function_service = (TextView) view.findViewById(R.id.function_service);

        home_activity_body = (RecyclerView) view.findViewById(R.id.home_activity_body);
        home_activity_body.setHasFixedSize(true);
        home_activity_body.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        activityAdapter = new ActivityAdapter(getContext(),home_activity_body);
        home_activity_body.setAdapter(activityAdapter);
        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
        snapHelperStart.attachToRecyclerView(home_activity_body);

        home_homing_body = (RecyclerView) view.findViewById(R.id.home_homing_body);
        home_homing_body.setHasFixedSize(true);
        home_homing_body.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        weather_update_time = (TextView) view.findViewById(R.id.weather_update_time);
        weather_temperature = (TextView) view.findViewById(R.id.weather_temperature);
        weather_skyIcon = (TextView) view.findViewById(R.id.weather_skyIcon);
        weather_aqi_description = (TextView) view.findViewById(R.id.weather_aqi_description);
        weather_location = (TextView) view.findViewById(R.id.weather_location);
        //weather_prompt_content = (TextView) view.findViewById(R.id.weather_prompt_content);
        weather_skyIcon_icon = (ImageView) view.findViewById(R.id.weather_skyIcon_icon);
        weather_aqi_icon = (ImageView) view.findViewById(R.id.weather_aqi_icon);
        weather_update_time.setText(WeatherUtil.serverTimeToString(server_time));
        weather_temperature.setText(temperature+"°");
        weather_skyIcon.setText(WeatherUtil.skyconToDescription(skycon));
        weather_aqi_description.setText(WeatherUtil.aqiToString(aqi));
        weather_location.setText(district);
        weather_skyIcon_icon.setImageResource(WeatherUtil.skyconToIconId(skycon));
        weather_aqi_icon.setImageResource(R.drawable.ic_aqi_while_128dp);

    }

    /**
     * 点击初始化
     */
    private void initClick(){
        function_announcement.setOnClickListener(this);
        function_grade.setOnClickListener(this);
        function_schedule.setOnClickListener(this);
        function_news.setOnClickListener(this);
        function_times.setOnClickListener(this);
        home_search_txt.setOnClickListener(this);
        function_library.setOnClickListener(this);
        function_calendar.setOnClickListener(this);
        function_service.setOnClickListener(this);
    }

    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 加载图片List
     * @param shufflingList 图片列表
     */
    @Override
    public void onLoadShufflingListSuccess(List<Shuffling> shufflingList) {
        this.shufflingList = shufflingList;
        //设置图片资源:url或本地资源
        for (int i = 0;i<shufflingList.size();i++){
            url.add(shufflingList.get(i).getImage().getUrl());
            Log.e("url",shufflingList.get(i).getImage().getUrl());
        }
        images= url.toArray(new String[url.size()]);
        for (int i = 0;i<images.length;i++){
            Log.e("im",images[i]);
        }
    }

    /**
     * 加载活动List
     * @param activitiesList
     */
    @Override
    public void onLoadActivityListSuccess(List<Activities> activitiesList) {
        activityAdapter.setActivitiesList(activitiesList);
    }

    /**
     * 加载寻物招领
     * @param homingList
     */
    @Override
    public void onLoadHomingListSuccess(List<Homing> homingList) {

    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        User user = BmobUser.getCurrentUser(User.class);
        switch (view.getId()){
            case R.id.function_announcement:
                Intent intent_announcement = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent_announcement);
                break;
            case R.id.function_grade:
                if (user != null){
                    if (user.getBinding()){
                        sendInfo("","", ConstantsUtil.GRADE);
                    } else {
                        notBinding();
                    }
                } else {
                    Snackbar snackbar= SnackBarUtil.LongSnackbar(home_CoordinatorLayout,"尚未登录"
                            ,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorPrimary)).setActionTextColor(Color.WHITE).setAction("去登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity)getActivity()).login();
                        }
                    });
                    snackbar.show();
                }
                break;
            case R.id.function_schedule:
                if (user != null){
                    if (user.getBinding()){
                        sendInfo("","", ConstantsUtil.SCHEDULE);
                    } else {
                        notBinding();
                    }
                } else {
                    Snackbar snackbar= SnackBarUtil.LongSnackbar(home_CoordinatorLayout,"尚未登录"
                            ,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorPrimary)).setActionTextColor(Color.WHITE).setAction("去登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity)getActivity()).login();
                        }
                    });
                    snackbar.show();
                }
                break;
            case R.id.function_news:
                Intent intent_news = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent_news);
                break;
            case R.id.function_times:
                if (user != null){
                    Intent intent_times = new Intent(getActivity(), TestActivity.class);
                    startActivity(intent_times);
                } else {
                    Snackbar snackbar= SnackBarUtil.LongSnackbar(home_CoordinatorLayout,"尚未登录"
                            ,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorPrimary)).setActionTextColor(Color.WHITE).setAction("去登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity)getActivity()).login();
                        }
                    });
                    snackbar.show();
                }
                break;
            case R.id.home_search_txt:
                //Intent intent_search = new Intent(getActivity(), SearchActivity.class);
                //startActivity(intent_search);
                break;
            case R.id.function_library:
                Snackbar snackbar= SnackBarUtil.ShortSnackbar(home_CoordinatorLayout,"图书馆暂未上线"
                        ,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorPrimary));
                snackbar.show();
                break;
            case R.id.function_calendar:
                Intent intent_calendar = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent_calendar);
                break;
            case R.id.function_service:
                Snackbar snackbar_service= SnackBarUtil.ShortSnackbar(home_CoordinatorLayout,"服务暂未上线"
                        ,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorPrimary));
                snackbar_service.show();
                break;
        }
    }

    /**
     * 跳转Activity
     * @param studentNum
     * @param password
     * @param code
     */
    @Override
    public void sendInfo(String studentNum, String password,int code) {
        switch (code){
            case ConstantsUtil.GRADE:
                Intent intent_grade = new Intent(getActivity(), GradeActivity.class);
                startActivity(intent_grade);
                break;
            case ConstantsUtil.SCHEDULE:
                Intent intent_schedule = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intent_schedule);
                break;
        }
    }

    /**
     * SnackBar提示条
     */
    @Override
    public void notBinding() {
        Snackbar snackbar= SnackBarUtil.LongSnackbar(home_CoordinatorLayout,"尚未绑定海师学号"
                ,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorPrimary)).setActionTextColor(Color.WHITE).setAction("去绑定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BindingActivity.class);
                startActivity(intent);
            }
        });
        snackbar.show();
    }
}
