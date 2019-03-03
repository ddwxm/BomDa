package com.demo.somnus.bomda.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.view.IView.ISettingsActivity;
import com.gyf.barlibrary.ImmersionBar;

import cn.bmob.v3.BmobUser;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Somnus on 2018/4/5.
 * 设置Activity
 */

public class SettingActivity extends AppCompatActivity implements ISettingsActivity
        ,View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    private TextView exit_settings,prompt_name_txt,prompt_name,notice_vibration;
    private RelativeLayout account_security_article;
    private Switch provincial_traffic_switch,video_playback_switch,night_pattern_switch;
    private Switch notice_new_message_switch,notice_vibration_switch;
    private RelativeLayout prompt_name_article,notice_vibration_article,theme_kind_article;

    public enum ResultCode { LOGOUT_SUCCESS, NOT_LOGOUT }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
        initClickListener();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init(){
        exit_settings = (TextView) findViewById(R.id.exit_settings);
        account_security_article = (RelativeLayout) findViewById(R.id.account_security_article);

        // switch
        provincial_traffic_switch = (Switch) findViewById(R.id.provincial_traffic_switch);
        video_playback_switch = (Switch) findViewById(R.id.video_playback_switch);
        night_pattern_switch = (Switch) findViewById(R.id.night_pattern_switch);
        notice_new_message_switch = (Switch) findViewById(R.id.notice_new_message_switch);
        notice_vibration_switch = (Switch) findViewById(R.id.notice_vibration_switch);

        prompt_name_article = (RelativeLayout) findViewById(R.id.prompt_name_article);
        notice_vibration_article = (RelativeLayout) findViewById(R.id.notice_vibration_article);
        prompt_name_txt = (TextView) findViewById(R.id.prompt_name_txt);
        prompt_name = (TextView) findViewById(R.id.prompt_name);
        notice_vibration = (TextView) findViewById(R.id.notice_vibration);
    }

    private void initClickListener(){
        exit_settings.setOnClickListener(this);
        account_security_article.setOnClickListener(this);
        provincial_traffic_switch.setOnCheckedChangeListener(this);
        video_playback_switch.setOnCheckedChangeListener(this);
        night_pattern_switch.setOnCheckedChangeListener(this);
        notice_new_message_switch.setOnCheckedChangeListener(this);
        notice_vibration_switch.setOnCheckedChangeListener(this);
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.exit_settings:
                BmobUser currentUser = BmobUser.getCurrentUser();
                if (currentUser!=null){
                    BmobUser.logOut();   //清除缓存用户对象
                    final Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    qq.removeAccount(true);
                    Toast.makeText(this,"退出成功",Toast.LENGTH_SHORT).show();
                    setResult(ResultCode.LOGOUT_SUCCESS.ordinal());
                    finish();
                }
                break;
            case R.id.account_security_article:
                Intent intent = new Intent(SettingActivity.this,AccountActivity.class);
                intent.putExtra("RelativeLayout","account");
                startActivity(intent);
                break;
        }
    }

}
