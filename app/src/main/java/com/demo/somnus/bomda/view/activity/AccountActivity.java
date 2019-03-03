package com.demo.somnus.bomda.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.BlacklistAdapter;
import com.demo.somnus.bomda.model.bean.Blacklist;
import com.demo.somnus.bomda.model.bean.QQInfo;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.AccountPresenter;
import com.demo.somnus.bomda.presenter.IPresenter.IAccountPresenter;
import com.demo.somnus.bomda.presenter.IPresenter.ISettingsPresenter;
import com.demo.somnus.bomda.util.ProgressDialogUtil;
import com.demo.somnus.bomda.util.StringUtil;
import com.demo.somnus.bomda.view.IView.IAccountActivity;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Somnus on 2018/4/5.
 * 账号管理Activity
 */

public class AccountActivity extends AppCompatActivity implements IAccountActivity
        ,SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{
    private RelativeLayout account,blacklist,blacklist_null,social_QQ_article;
    private String relativeLayout;
    private ISettingsPresenter iSettingsPresenter;
    private TextView account_email_txt,account_phone_txt,social_student_txt;
    private TextView email_state_txt,phone_state_txt,social_QQ_txt;
    private ImageView email_state_icon,phone_state_icon,qq_state_icon;
    private Toolbar settings_include_toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private BlacklistAdapter blacklistAdapter;
    private IAccountPresenter iAccountPresenter;
    private QQInfo qqInfo;
    private ProgressDialogUtil progressDialogUtil;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
                    qqInfo = (QQInfo) msg.obj;
                    iAccountPresenter.qqBinding(qqInfo);
                    progressDialogUtil.setIngState("正在注册...");
                    progressDialogUtil.show();
                    break;
                case 2:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentBar().statusBarColor(R.color.colorPrimary).init();
        iAccountPresenter = new AccountPresenter(this);
        setContentView(R.layout.activity_account);
        initIntent();
        init();
        showRelativeLayout(relativeLayout);
        initClickListener();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initIntent(){
        Intent intent = getIntent();
        relativeLayout = intent.getStringExtra("RelativeLayout");
    }

    private void init(){
        progressDialogUtil = new ProgressDialogUtil(this);
        settings_include_toolbar = (Toolbar) findViewById(R.id.settings_include_toolbar);
        switch (relativeLayout){
            case "account":
                settings_include_toolbar.setTitle(R.string.account_security);
                break;
            case "blacklist":
                settings_include_toolbar.setTitle(R.string.account_blacklist);
                break;
        }
        settings_include_toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.setSupportActionBar(settings_include_toolbar);
        account = (RelativeLayout) findViewById(R.id.account_rl);
        blacklist = (RelativeLayout) findViewById(R.id.blacklist_rl);
        blacklist_null = (RelativeLayout) findViewById(R.id.blacklist_null);
        account_email_txt = (TextView) findViewById(R.id.account_email_txt);
        account_phone_txt = (TextView) findViewById(R.id.account_phone_txt);
        social_student_txt = (TextView) findViewById(R.id.social_student_txt);
        email_state_txt = (TextView) findViewById(R.id.email_state_txt);
        phone_state_txt = (TextView) findViewById(R.id.phone_state_txt);
        email_state_icon = (ImageView) findViewById(R.id.email_state_icon);
        phone_state_icon = (ImageView) findViewById(R.id.phone_state_icon);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.blacklist_sw);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.blacklist_re);
        recyclerView.setLayoutManager(new LinearLayoutManager(AccountActivity.this));
        blacklistAdapter = new BlacklistAdapter(AccountActivity.this,recyclerView);
        recyclerView.setAdapter(blacklistAdapter);

        social_QQ_article = (RelativeLayout) findViewById(R.id.social_QQ_article);
        social_QQ_txt = (TextView) findViewById(R.id.social_QQ_txt);
        qq_state_icon = (ImageView) findViewById(R.id.qq_state_icon);
    }

    private void showInfo(User user){
        if (user != null){
            if (!user.getEmail().equals("")){
                account_email_txt.setText(user.getEmail());
                email_state_txt.setText(R.string.account_authenticated);
                email_state_icon.setImageResource(R.drawable.ic_security_blue_24dp);
            } else {
                account_email_txt.setText(R.string.account_email_txt);
                email_state_txt.setText(R.string.account_verify);
                email_state_icon.setImageResource(R.drawable.ic_security_grey_24dp);
            }
            if (!user.getMobilePhoneNumber().equals("")){
                account_phone_txt.setText(StringUtil.changePhone(user.getMobilePhoneNumber()));
                phone_state_txt.setText(R.string.account_authenticated);
                phone_state_icon.setImageResource(R.drawable.ic_security_blue_24dp);
            } else {
                account_phone_txt.setText(R.string.account_phone_txt);
                phone_state_txt.setText(R.string.account_verify);
                phone_state_icon.setImageResource(R.drawable.ic_security_grey_24dp);
            }
            if (!user.getStudentId().equals("")){
                social_student_txt.setText(StringUtil.changeStudentId(user.getStudentId()));
            } else {
                social_student_txt.setText(R.string.social_not_bound);
            }
            if (user.getQqBinding()){
                social_QQ_txt.setText(R.string.social_is_bound);
                qq_state_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_social_qq_islogin_colorful_24dp));
            } else {
                social_QQ_txt.setText(R.string.social_not_bound);
                qq_state_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_social_qq_nologin_colorful_24dp));
            }
        }

    }

    private void showRelativeLayout(String relativeLayout){
        switch (relativeLayout){
            case "account":
                account.setVisibility(View.VISIBLE);
                blacklist.setVisibility(View.GONE);
                settings_include_toolbar.setTitle(R.string.account_security);
                showInfo(BmobUser.getCurrentUser(User.class));
                break;
            case "blacklist":
                blacklist.setVisibility(View.VISIBLE);
                account.setVisibility(View.GONE);
                settings_include_toolbar.setTitle(R.string.account_blacklist);
                swipeRefreshLayout.setRefreshing(true);
                break;
        }
    }

    private void initClickListener(){
        social_QQ_article.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.social_QQ_article:
                if (BmobUser.getCurrentUser(User.class).getQqBinding()){

                } else {
                    final QQInfo qqInfo = new QQInfo();
                    final Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                    qq.setPlatformActionListener(new PlatformActionListener() {

                        @Override
                        public void onError(Platform arg0, int arg1, Throwable arg2) {
                            // TODO Auto-generated method stub
                            arg2.printStackTrace();
                            qq.removeAccount(true);
                        }

                        @Override
                        public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                            // TODO Auto-generated method stub
                            //输出所有授权信息
                            arg0.getDb().exportData();
                            if (arg1 == Platform.ACTION_USER_INFOR) {
                                PlatformDb platDB = arg0.getDb();//获取数平台数据DB
                                //通过DB获取各种数据
                                platDB.getToken();
                                platDB.getUserGender();
                                platDB.getUserIcon();
                                platDB.getUserId();
                                platDB.getUserName();
                                Log.e("QQSex",platDB.getUserGender());
                                Log.e("QQIcon",platDB.getUserIcon());
                                Log.e("QQId",platDB.getUserId());
                                Log.e("QQName",platDB.getUserName());
                                Log.e("HashMap",arg2.toString());
                                Log.e("HashMap", (String) arg2.get("city"));
                                qqInfo.setName(platDB.getUserName());
                                qqInfo.setAvatar(platDB.getUserIcon());
                                qqInfo.setId(platDB.getUserId());
                                qqInfo.setCity(String.valueOf(arg2.get("city")));
                                qqInfo.setSex((String) arg2.get("gender"));
                                Message msg =Message.obtain();
                                msg.obj = qqInfo;
                                msg.what=1;   //标志消息的标志
                                handler.sendMessage(msg);
                            }
                        }

                        @Override
                        public void onCancel(Platform arg0, int arg1) {
                            // TODO Auto-generated method stub

                        }
                    });
                    //authorize与showUser单独调用一个即可
                    //qq.authorize();//单独授权,OnComplete返回的hashmap是空的
                    qq.showUser(null);//授权并获取用户信息
                    // 移除授权
                    // weibo.removeAccount(true);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        iAccountPresenter.search(AccountActivity.this,BmobUser.getCurrentUser(User.class));
    }

    @Override
    public void refreshSuccess() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void refreshFail() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoadBlacklistListSuccess(List<Blacklist> addressList) {
        blacklist.setVisibility(View.VISIBLE);
        blacklistAdapter.setBlacklistList(addressList);
    }

    @Override
    public void sendNull(Integer size) {
        blacklist.setVisibility(View.GONE);
        blacklist_null.setVisibility(View.VISIBLE);
    }

    @Override
    public void BindingSuccess(User user, String type) {
        progressDialogUtil.setSuccessState("绑定成功");
        progressDialogUtil.dismissDelay(new ProgressDialogUtil.OnDelayDismissCallback() {
            @Override
            public void onDismiss() {
            }
        });
        showInfo(user);
    }

    @Override
    public void BindingFail(String msg, int code) {
        progressDialogUtil.setFailureState("绑定失败", code, msg);
        progressDialogUtil.setActionBtn("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogUtil.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
