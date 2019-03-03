package com.demo.somnus.bomda.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.AppData;
import com.demo.somnus.bomda.model.bean.InfoNum;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IWelcomePresenter;
import com.demo.somnus.bomda.presenter.WelcomePresenter;
import com.demo.somnus.bomda.view.IView.IWelcomeActivity;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobUser;

/**
 * Created by Somnus on 2018/4/4.
 * 欢迎Activity
 */

public class WelcomeActivity extends AppCompatActivity implements IWelcomeActivity,View.OnClickListener{


    private Timer timer;
    private Button btnSkip;
    private ImageView imvWelcome;
    private IWelcomePresenter iWelcomePresenter;
    private String district_loc;
    private String temperature,skycon;
    private ImageView welcome_name;
    private long server_time;
    private double aqi;
    private AppData appData;
    private InfoNum infoNum = new InfoNum();


    private final long DELAY = 3000; //自动跳转的延时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setTheme(R.style.AppTheme_NoActionBar_Fullscreen);
        setContentView(R.layout.activity_welcome);
        iWelcomePresenter = new WelcomePresenter(this);

        initWidget();
    }

    /**
     *
     *private void initTools(){
        appData = new AppData();
        int TOOLS_SKIN = SharedPreferencesUtils
                .getInt(Constants.SHARD_NAME, Constants.TOOLS_SKIN, -1);
        boolean TOOLS_TRAFFIC = SharedPreferencesUtils.getBoolean(Constants
                .SHARD_NAME, Constants.TOOLS_TRAFFIC, false);
        boolean TOOLS_VIDEO = SharedPreferencesUtils.getBoolean(Constants
                .SHARD_NAME, Constants.TOOLS_VIDEO, false);
        boolean TOOLS_NIGHT = SharedPreferencesUtils.getBoolean(Constants
                .SHARD_NAME, Constants.TOOLS_NIGHT, false);
        boolean TOOLS_MESSAGE = SharedPreferencesUtils.getBoolean(Constants
                .SHARD_NAME, Constants.TOOLS_MESSAGE, false);
        boolean TOOLS_VIBRATION = SharedPreferencesUtils.getBoolean(Constants
                .SHARD_NAME, Constants.TOOLS_VIBRATION, false);
        appData.setProvincial_traffic(TOOLS_TRAFFIC);
        appData.setVideo_playback(TOOLS_VIDEO);
        appData.setLux(TOOLS_NIGHT);
        appData.setTheme_kind(TOOLS_SKIN);

    }
     *
     */

    @Override
    protected void onResume() {
        super.onResume();
        autoStartMainActivity(DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_skip:
                // btnSkip
                cancelTimer();
                startMainActivity();
                break;
        }
    }

    /**
     * 如果 timer 正在执行，就取消他
     */
    private void cancelTimer() {
        // 如果 timer 正在执行，就取消他
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 实例化、初始化所有控件
     */
    private void initWidget() {
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(this);
        imvWelcome = (ImageView) findViewById(R.id.imv_welcome);
        welcome_name = (ImageView) findViewById(R.id.welcome_name);
        // 判断网络连接是否正常
        if (!isNetworkAvailable()) {
            Toast.makeText(this,"设备无网络，请连接到 WiFi 或数据网络，再试一次。",Toast.LENGTH_SHORT).show();
            district_loc = "";
            temperature = "";
            skycon = "";
            infoNum.setFocusNum(0);
            infoNum.setCollectionNum(0);
            infoNum.setPraiseNum(0);
            infoNum.setDynamicNum(0);
        } else {
            if (BmobUser.getCurrentUser(User.class) != null){
                iWelcomePresenter.getNum(BmobUser.getCurrentUser(User.class));
            }
            iWelcomePresenter.getWeather(this);
        }
        iWelcomePresenter.showWelcomePicture(this);
    }

    /**
     * 显示广告图片
     * @param pictureURL 图片的 url 地址
     */
    @Override
    public void showWelcomePicture(String pictureURL) {
        // 如果 activity 已经不存在了就跳过这个方法
        if (isDestroyed() || isFinishing()) { return; }

        Glide.with(getApplicationContext())
                .load(pictureURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //让 Glide 既缓存全尺寸又缓存其他尺寸
                .crossFade(120)
                .error(R.mipmap.mine_blue_bg)
                .centerCrop()
                .into(imvWelcome);
    }

    /**
     * 延迟 delay 秒后自动进入主界面
     * @param delay 延时
     */
    private void autoStartMainActivity(final long delay) {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int repeatCount = (int) (delay / 1000);
            @Override
            public void run() { runOnUiThread( new Runnable() { @Override public void run() {
                String btnSkipText = repeatCount + "s " ;
                btnSkip.setText(btnSkipText);
                if (repeatCount == 0) {
                    timer.cancel(); // 中止计时器
                    startMainActivity();
                }
                repeatCount -= 1;
            }});}
        };
        timer.schedule(timerTask, 0, 1000); // 立即执行，1000ms 重复一次
    }

    /**
     * 启动主界面
     */
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("district",district_loc);
        intent.putExtra("temperature",temperature);
        intent.putExtra("skyIcon",skycon);
        intent.putExtra("server_time",server_time);
        intent.putExtra("aqi",aqi);
        intent.putExtra("dynamic",infoNum.getDynamicNum());
        intent.putExtra("praise",infoNum.getPraiseNum());
        intent.putExtra("focus",infoNum.getFocusNum());
        intent.putExtra("collection",infoNum.getCollectionNum());
        startActivity(intent);
        finish();
    }

    @Override
    public void sendLocationInfo(double latitude, double longitude, String district, String city, String street, String address) {
        //Toast.makeText(this,latitude+" "+longitude,Toast.LENGTH_SHORT).show();
        Log.w("district",district);
        Log.w("street",street);
        Log.w("str",address);
        Log.w("city",city);
        district_loc = district;
    }

    @Override
    public void sendWeather(double temperature, String skycon, long server_time, Double aqi) {
        this.temperature = Double.toString(temperature);
        this.skycon = skycon;
        this.server_time = server_time;
        this.aqi = aqi;
    }

    @Override
    public void num(InfoNum infoNum) {
        this.infoNum = infoNum;
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
