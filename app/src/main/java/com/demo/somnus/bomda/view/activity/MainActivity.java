package com.demo.somnus.bomda.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.callback.ByteCallBack;
import com.demo.somnus.bomda.callback.CodeCallBack;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IMainPresenter;
import com.demo.somnus.bomda.presenter.MainPresenter;
import com.demo.somnus.bomda.view.IView.IMainActivity;
import com.demo.somnus.bomda.view.fragment.ExploreFragment;
import com.demo.somnus.bomda.view.fragment.HomeFragment;
import com.demo.somnus.bomda.view.fragment.MineFragment;
import com.gyf.barlibrary.ImmersionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import cn.bmob.v3.BmobUser;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements IMainActivity
        ,MenuItem.OnMenuItemClickListener {
    private IMainPresenter iMainPresenter;
    private String district,locationdescribe,temperature,skycon;
    public static  String XH;
    private BottomNavigationView bnv;
    private ExploreFragment exploreFragment;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    // 当前显示的 fragment
    private Fragment fromFragment = null;
    public enum RequestCode { LOGIN, LOGOUT,SITING }


    public static final int REQUEST_CODE_PUBLISH_NEW_DYNAMIC = 379;

    private long server_time;
    private Double aqi;
    private int dynamic,focus,collection,praise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentBar().statusBarDarkFont(true, 0.2f).init();
        iMainPresenter = new MainPresenter(this);
        setContentView(R.layout.activity_main);
        initIntent();
        initWidget();

        // 默认显示 homeFragment
        switchFragment(homeFragment);
        homeFragment.getTemperature(district,temperature,skycon,server_time,aqi);
        mineFragment.getNum(dynamic,praise,collection,focus);

    }

    private void initWidget() {
        // 实例化 fragment
        exploreFragment = new ExploreFragment();
        homeFragment = new HomeFragment();
        mineFragment = new MineFragment();
        // fixSomeFragmentBug(); 然而这个方法并没有什么用
        // 初始化 BottomNavigationView
        bnv = (BottomNavigationView) findViewById(R.id.main_bnv);
        Menu bnvMenu = bnv.getMenu();
        bnvMenu.findItem(R.id.navigation_home).setOnMenuItemClickListener(this);
        bnvMenu.findItem(R.id.navigation_life).setOnMenuItemClickListener(this);
        bnvMenu.findItem(R.id.navigation_mine).setOnMenuItemClickListener(this);
    }

    /**
     * 切换 fragment
     * @param toFragment 将要显示的 fragment
     */
    private void switchFragment(Fragment toFragment) {
        if (fromFragment != null) {
            // 如果当前显示的 fragment 存在
            if (fromFragment.equals(toFragment)) {
                // 但却和将要显示的相同，那么就什么也不做
                return;
            } else if (!toFragment.isAdded()) {
                // 并且将要显示的 fragment 未被添加过
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .hide(fromFragment) // 隐藏当前显示的
                        .add(R.id.main_fragment, toFragment) // 添加并显示将要显示的
                        .commit();
            } else {
                // 并且将要显示的 fragment 也存在
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .hide(fromFragment) // 隐藏当前显示的
                        .show(toFragment) // 显示出即将要显示的
                        .commit();
            }
        } else {
            // 如果当前显示的 fragment 不存在（一般在第一次挑用这个方法时）
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .add(R.id.main_fragment, toFragment) // 直接显示将要显示的即可
                    .commit();
        }
        fromFragment = toFragment; // 更新当前 fragment
    }

    /**
     * 希望可以避免一些奇怪的问题
     */
    private void fixSomeFragmentBug() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(homeFragment)
                .remove(exploreFragment)
                .remove(mineFragment)
                .commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                switchFragment(homeFragment);
                break;
            case R.id.navigation_life:
                switchFragment(exploreFragment);
                break;
            case R.id.navigation_mine:
                switchFragment(mineFragment);
                break;
        }
        return false;
    }

    private void initIntent() {
        Intent intent = getIntent();
        district = intent.getStringExtra("district");
        temperature = intent.getStringExtra("temperature");
        skycon = intent.getStringExtra("skyIcon");
        server_time = intent.getLongExtra("server_time",0);
        aqi = intent.getDoubleExtra("aqi",0);
        dynamic = intent.getIntExtra("dynamic",0);
        focus = intent.getIntExtra("focus",0);
        collection = intent.getIntExtra("collection",0);
        praise = intent.getIntExtra("praise",0);
    }

    public void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, RequestCode.LOGIN.ordinal());
    }

    public void info(User currentUser){
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("anotherUserObjectId", currentUser.getObjectId());
        startActivityForResult(intent, RequestCode.SITING.ordinal());
    }

    public void settings(){
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra("isCurrentUser", true);
        startActivityForResult(intent, RequestCode.LOGOUT.ordinal());
    }

    public void postTease(){
        Intent intent = new Intent(this, PostTeaseActivity.class);
        intent.putExtra("location",district);
        startActivityForResult(intent, REQUEST_CODE_PUBLISH_NEW_DYNAMIC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.LOGIN.ordinal()) {
            if (resultCode == LoginActivity.ResultCode.LOGIN_SUCCESS.ordinal()) {
                mineFragment.isLogin();
                homeFragment.getTemperature(district,temperature,skycon,server_time,aqi);
            } else {
                // do noting
            }
        } else if (requestCode == RequestCode.LOGOUT.ordinal()) {
            if (resultCode == SettingActivity.ResultCode.LOGOUT_SUCCESS.ordinal()) {
                mineFragment.isLogin();
            } else {
                // do noting
            }
        } else if (requestCode == RequestCode.SITING.ordinal()){
            if (resultCode == PersonActivity.RequestCode.SETIN.ordinal()){
                User user = BmobUser.getCurrentUser(User.class);
                mineFragment.showCurrentUserInfo(user);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }


}
