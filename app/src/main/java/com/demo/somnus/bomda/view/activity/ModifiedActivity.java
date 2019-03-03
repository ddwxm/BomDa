package com.demo.somnus.bomda.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.util.DateTimeUtil;
import com.demo.somnus.bomda.util.InputTextDialog;
import com.demo.somnus.bomda.util.UploadPicturesDialog;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.UpdateListener;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Somnus on 2018/4/4.
 * 编辑个人资料Activity
 */

public class ModifiedActivity extends AppCompatActivity implements View.OnClickListener
        ,UploadPicturesDialog.UploadPicturesDoneCallback{
    private RelativeLayout modified_rl_avatar,modified_rl_background;
    private ImageView avatar_imv,background_imv;
    private TextView nick_txt,sex_txt,birth_txt,region_txt,signature_txt,introduction_txt;
    private TextView avatar_txt,background_txt;
    private int yourChoice;
    private boolean isChoice = false;
    private static final int REQUEST_CODE_CHOOSE_AVATAR = 803;
    private static final int REQUEST_CODE_PERMISSION_STORAGE = 168;
    private int year,month,day;
    private int Year=0,Month=0,Day=0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
                    String region = (String) msg.obj;
                    User user = new User();
                    user.setAddress(region);
                    user.update(User.getCurrentUser().getObjectId(), new UpdateUserInfoListener());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified);

        getDate();
        init();
        initClick();
        showUserInfo(BmobUser.getCurrentUser(User.class));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化控件
     */
    private void init(){
        changeDate(BmobUser.getCurrentUser(User.class).getBirth());
        avatar_txt = (TextView) findViewById(R.id.avatar_txt);
        background_txt = (TextView) findViewById(R.id.background_txt);

        avatar_imv = (ImageView) findViewById(R.id.avatar_imv);
        background_imv = (ImageView) findViewById(R.id.background_imv);

        nick_txt = (TextView) findViewById(R.id.nick_txt);
        sex_txt = (TextView) findViewById(R.id.sex_txt);
        birth_txt = (TextView) findViewById(R.id.birth_txt);
        region_txt = (TextView) findViewById(R.id.region_txt);
        signature_txt = (TextView) findViewById(R.id.signature_txt);
        introduction_txt = (TextView) findViewById(R.id.introduction_txt);
    }

    /**
     * 注册点击事件
     */
    private void initClick(){
        avatar_txt.setOnClickListener(this);
        background_txt.setOnClickListener(this);
        nick_txt.setOnClickListener(this);
        sex_txt.setOnClickListener(this);
        birth_txt.setOnClickListener(this);
        region_txt.setOnClickListener(this);
        signature_txt.setOnClickListener(this);
        introduction_txt.setOnClickListener(this);
    }

    /**
     * 加载用户信息
     * @param user
     */
    public void showUserInfo(User user){
        if (user != null){
            if (user.getQqLogin()){
                Glide.with(this.getApplicationContext())
                        .load(user.getQqavatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar_24dp)
                        .bitmapTransform(new CropCircleTransformation(this))
                        .into(avatar_imv);
                Glide.with(this.getApplicationContext())
                        .load(R.mipmap.mine_blue_bg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.side_nav_bar)
                        .into(background_imv);
            } else {
                BmobFile avatar = user.getAvatar();
                BmobFile background = user.getBackground();
                if (avatar != null){
                    Glide.with(this.getApplicationContext())
                            .load(user.getAvatar().getFileUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_avatar_24dp)
                            .bitmapTransform(new CropCircleTransformation(this))
                            .into(avatar_imv);
                } else {
                    avatar_imv.setImageResource(R.drawable.ic_avatar_24dp);
                }
                if (background != null){
                    Glide.with(this.getApplicationContext())
                            .load(user.getBackground().getFileUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.side_nav_bar)
                            .into(background_imv);
                } else {
                    background_imv.setImageResource(R.mipmap.mine_blue_bg);
                }
            }
            nick_txt.setText(user.getNick());
            sex_txt.setText(user.getSex());
            birth_txt.setText(user.getBirth());
            region_txt.setText(user.getAddress());
            signature_txt.setText(user.getSignature());
            introduction_txt.setText(user.getIntroduction());
        }
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.avatar_txt:
                isChoice = false;
                choosePicture(hasStoragePermission());
                break;
            case R.id.background_txt:
                isChoice = true;
                choosePicture(hasStoragePermission());
                break;
            case R.id.nick_txt:
                InputTextDialog dialog_nick = new InputTextDialog(this, "修改昵称");
                dialog_nick.setHint("新昵称");
                dialog_nick.setMinCount(2);
                dialog_nick.setMaxCount(5);
                dialog_nick.setSingleLine();
                dialog_nick.setOnOkCallback(new InputTextDialog.OnOkCallback() {
                    @Override
                    public void onOk(String text) {
                        User user = new User();
                        user.setNick(text);
                        user.update(User.getCurrentUser().getObjectId(), new UpdateUserInfoListener());
                    }
                });
                dialog_nick.show();
                break;
            case R.id.sex_txt:
                showSingleChoiceDialog(BmobUser.getCurrentUser(User.class));
                break;
            case R.id.birth_txt:
                birthChoice();
                break;
            case R.id.region_txt:
                break;
            case R.id.signature_txt:
                InputTextDialog dialog_signature = new InputTextDialog(this, "修改签名");
                dialog_signature.setHint("新签名");
                dialog_signature.setMinCount(2);
                dialog_signature.setMaxCount(24);
                dialog_signature.setSingleLine();
                dialog_signature.setOnOkCallback(new InputTextDialog.OnOkCallback() {
                    @Override
                    public void onOk(String text) {
                        User user = new User();
                        user.setSignature(text);
                        user.update(User.getCurrentUser().getObjectId(), new UpdateUserInfoListener());
                    }
                });
                dialog_signature.show();
                break;
            case R.id.introduction_txt:
                InputTextDialog dialog_introduction = new InputTextDialog(this, "修改介绍");
                dialog_introduction.setHint("新介绍");
                dialog_introduction.setMinCount(2);
                dialog_introduction.setMaxCount(56);
                dialog_introduction.setSingleLine();
                dialog_introduction.setOnOkCallback(new InputTextDialog.OnOkCallback() {
                    @Override
                    public void onOk(String text) {
                        User user = new User();
                        user.setIntroduction(text);
                        user.update(User.getCurrentUser().getObjectId(), new UpdateUserInfoListener());
                    }
                });
                dialog_introduction.show();
                break;
        }
    }

    /**
     * 获取当前时间年月日
     */
    private void getDate(){
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.setTime(curDate);
        year = calendar.get(java.util.Calendar.YEAR);
        month = calendar.get(java.util.Calendar.MONTH)+1;
        day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }

    /**
     * 改变数据库生日格式
     * @param birth 生日
     */
    private void changeDate(String birth){
        if (birth.equals("未填写")){
            Year = year;
            Month = month;
            Day = day;
        } else {
            Year = DateTimeUtil.formatting(birth,4);
            Month = DateTimeUtil.formatting(birth,5);
            Day = DateTimeUtil.formatting(birth,0);
        }
    }

    /**
     * 生日日期选择框
     */
    private void birthChoice(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (year>Year||(year==Year&&monthOfYear+1>month)|| (year==Year&&monthOfYear+1 == month&&dayOfMonth>day)){
                    Toast.makeText(ModifiedActivity.this,"所选日期不能大于今天",Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setBirth(year+"-"+(monthOfYear + 1)+"-"+dayOfMonth);
                    user.update(User.getCurrentUser().getObjectId(), new UpdateUserInfoListener());
                }
            }
        },Year,Month-1,Day);
        datePickerDialog.show();
    }

    /**
     * 性别单选框
     * @param user
     */
    private void showSingleChoiceDialog(User user){
        final String[] items = { "男","女" };
        switch (user.getSex()){
            case "男":
                yourChoice = 0;
                break;
            case "女":
                yourChoice = 1;
                break;
        }
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(ModifiedActivity.this);
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, yourChoice,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            User user = new User();
                            user.setSex(items[yourChoice]);
                            user.update(User.getCurrentUser().getObjectId(), new UpdateUserInfoListener());
                        }
                    }
                });
        singleChoiceDialog.show();
    }

    /**
     * 上传照片回调
     * @param files
     * @param e
     */
    @Override
    public void onUploadPicturesDone(List<BmobFile> files, BmobException e) {
        if (e == null) {
            if (isChoice){
                updateUserBackGround(files.get(0));
            } else {
                updateUserAvatar(files.get(0));
            }
        } else {
            final int code = e.getErrorCode();
            if (isChoice){
                Toast.makeText(ModifiedActivity.this,"修改主页背景失败("+code+")",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ModifiedActivity.this,"修改头像失败("+code+")",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_STORAGE) {
            //无需再次检查权限，所以直接调用 selectPictures(boolean hasPermission) 方法
            //pictureUploadAdapter.enableUploadBtn();
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                choosePicture(true);
            } else {
                Toast.makeText(ModifiedActivity.this,"请授予内置存储的读写权限后再使用本功能!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 选择图片
     * @param hasPermission 是否有权限
     */
    private void choosePicture(boolean hasPermission) {
        if (!hasPermission) return;
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE_AVATAR);
    }

    /**
     * 检查 storage 的读写权限
     * @return false：没有权限，正在申请
     *         true：有权限
     */
    private boolean hasStoragePermission() {
        int readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> permissionList = new ArrayList<>();
        if (readStorage == PackageManager.PERMISSION_DENIED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writeStorage == PackageManager.PERMISSION_DENIED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionList.size() != 0) {
            String[] permissions = new String[permissionList.size()];
            permissionList.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION_STORAGE);
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission_group.STORAGE)) {
                Toast.makeText(ModifiedActivity.this,"需要内置存储的读写权限上传照片哦!",Toast.LENGTH_SHORT).show();
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_AVATAR:
                if (resultCode == RESULT_OK) {
                    String avatarPath = Matisse.obtainPathResult(data).get(0);
                    UploadPicturesDialog.create(this, "上传中...").startUpload(avatarPath, this);
                }
                break;
        }
    }

    /**
     * 修改用户头像
     * @param avatar 新头像
     */
    private void updateUserAvatar(BmobFile avatar) {
        User curUser = User.getCurrentUser(User.class);
        if (curUser != null) {
            //更新用户头像
            final BmobFile oldAvatar = curUser.getAvatar();
            User user = User.nullUser();
            user.setAvatar(avatar);
            user.update(curUser.getObjectId(),
                    new UpdateUserInfoListener("修改头像成功", "修改头像失败") {
                        @Override
                        public void done(BmobException e) {
                            super.done(e);
                            if (e == null) {
                                if (oldAvatar != null) { //考虑到新用户没有头像的情况
                                    oldAvatar.delete(new UpdateListener() { //从服务器删除原头像
                                        @Override
                                        public void done(BmobException e) {}
                                    });
                                }
                            }
                        }
                    });
        } else {
            Toast.makeText(ModifiedActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 修改用户主页背景
     * @param background
     */
    private void updateUserBackGround(BmobFile background){
        User curUser = User.getCurrentUser(User.class);
        if (curUser != null) {
            //更新用户主页背景
            final BmobFile oldBackground = curUser.getBackground();
            User user = User.nullUser();
            user.setBackground(background);
            user.update(curUser.getObjectId(),
                    new UpdateUserInfoListener("修改背景成功", "修改背景失败") {
                        @Override
                        public void done(BmobException e) {
                            super.done(e);
                            if (e == null) {
                                if (oldBackground != null) { //考虑到新用户没有头像的情况
                                    oldBackground.delete(new UpdateListener() { //从服务器删除原头像
                                        @Override
                                        public void done(BmobException e) {}
                                    });
                                }
                            }
                        }
                    });
        } else {
            Toast.makeText(ModifiedActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 默认的用户信息修改监听器
     */
    private class UpdateUserInfoListener extends UpdateListener {

        private String successToastText;
        private String failureToastText;

        UpdateUserInfoListener() {
            successToastText = "修改成功";
            failureToastText = "修改失败";
        }

        @Override
        public void done(BmobException e) {
            if (e == null) {
                Log.e("change","change1");
                Toast.makeText(ModifiedActivity.this, successToastText,Toast.LENGTH_SHORT).show();
                User.fetchUserJsonInfo(new FetchUserInfoListener<String>() { //更新本地用户信息
                    @Override
                    public void done(String s, BmobException e) {
                        Gson gson = new Gson();
                        User user = gson.fromJson(s,User.class);
                        User user1 = BmobUser.getCurrentUser(User.class);
                        user1.setEmail(user.getEmail());
                        user1.setNick(user.getNick());
                        user1.setBackground(user.getBackground());
                        user1.setSignature(user.getSignature());
                        user1.setIntroduction(user.getIntroduction());
                        user1.setAddress(user.getAddress());
                        user1.setBirth(user.getBirth());
                        user1.setAvatar(user.getAvatar());
                        user1.setSex(user.getSex());
                        Log.e("change","change");
                        showUserInfo(user1);
                    }
                });
            } else {
                final int code = e.getErrorCode();
                if (code == 206) {
                    Toast.makeText(ModifiedActivity.this,
                            failureToastText + "(" + code + ")，请先登录再操作",Toast.LENGTH_SHORT).show();
                    User.logOut();
                    finish();
                } else {
                    Toast.makeText(ModifiedActivity.this, failureToastText + "(" + code + ")",Toast.LENGTH_SHORT).show();
                }
            }
        }

        UpdateUserInfoListener(String successToastText, String failureToastText) {
            this.successToastText = successToastText;
            this.failureToastText = failureToastText;
        }
    }


    @Override
    public void onBackPressed() {
        // When the user hits the back button set the resultCode
        // as Activity.RESULT_CANCELED to indicate a failure
        setResult(Activity.RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
        return false;
    }
}
