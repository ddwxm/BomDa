package com.demo.somnus.bomda.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.adapter.PictureUploadAdapter;
import com.demo.somnus.bomda.listener.EditTextScrollOnScrollView;
import com.demo.somnus.bomda.listener.PublishDynamicListener;
import com.demo.somnus.bomda.listener.SimpleTextWatcher;
import com.demo.somnus.bomda.model.bean.Tease;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.util.ToastUtil;
import com.demo.somnus.bomda.util.UploadPicturesDialog;
import com.demo.somnus.bomda.view.IView.IPostTeaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Somnus on 2018/4/12.
 * 发布树洞Activity
 */

public class PostTeaseActivity extends AppCompatActivity implements IPostTeaseActivity
        ,PublishDynamicListener.PublishDoneCallback,
        UploadPicturesDialog.UploadPicturesDoneCallback {

    private static final String TAG = "PublishDynamicActivity";

    private static final int REQUEST_CODE_CHOOSE_PICTURE = 660;
    private static final int REQUEST_CODE_PERMISSION_STORAGE = 275;

    private NestedScrollView nsv;
    private RelativeLayout rlSwitchLeibie;
    private TextView tvLeibie;
    private TextInputLayout til_dynamic_content;
    private RecyclerView rvPictureUpload;
    private PictureUploadAdapter pictureUploadAdapter;
    private PopupWindow popupSwitchLeibie;
    private TextView dynamic_location;
    private String district;

    private Tease tease;
    private int maxSelectable = 0;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_tease);
        //setTitle(getText(R.string.activity_publish_dynamic_title));\
        district = getIntent().getStringExtra("location");
        if (User.getCurrentUser() == null) {
            ToastUtil.showLong(this, "请先登录");
            finish();
            return;
        }
        /* 恢复状态 */
        Serializable savedDynamic;
        if (savedState == null || (savedDynamic = savedState.getSerializable("Dynamic")) == null) {
            tease = Tease.newTease();
            tease.setAuthor(User.getCurrentUser(User.class));
            tease.setAddress(district);
        } else {
            tease = (Tease) savedDynamic;
        }
        ArrayList<String> picturePaths = null;
        if (savedState != null) {
            picturePaths = savedState.getStringArrayList("PicturePaths");
        }
        pictureUploadAdapter = new PictureUploadAdapter(this, picturePaths);
        initWidget();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * 初始化界面控件
     */
    private void initWidget() {
        nsv = (NestedScrollView) findViewById(R.id.nsv);
        til_dynamic_content = (TextInputLayout) findViewById(R.id.til_dynamic_content);
        final EditText etContent = til_dynamic_content.getEditText();
        etContent.setOnTouchListener(new EditTextScrollOnScrollView(etContent, nsv));
        etContent.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.e(TAG, "before: "+s+", "+start+", "+count+", "+before);
                tease.setContent(s.toString());
            }
        });
        dynamic_location = (TextView) findViewById(R.id.dynamic_location);
        dynamic_location.setText(district);
        rvPictureUpload = (RecyclerView) findViewById(R.id.rl_picture_upload);
        rvPictureUpload.setLayoutManager(new GridLayoutManager(this, 3)); //horizontal
        rvPictureUpload.setItemAnimator(new DefaultItemAnimator());
        rvPictureUpload.setHasFixedSize(true);
        rvPictureUpload.setAdapter(pictureUploadAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("PicturePaths", pictureUploadAdapter.getRealPicturePaths());
        outState.putSerializable("Dynamic", tease);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_publish_dynamic, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_publish:
                publishDynamic();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_PICTURE) {
            pictureUploadAdapter.enableUploadBtn();
            if (resultCode == RESULT_OK) {
                for (Uri uri : Matisse.obtainResult(data)) {
                    Log.e(TAG, "uri: "+uri.toString());
                }
                for (String path : Matisse.obtainPathResult(data)) {
                    Log.e(TAG, "path: "+path);
                    pictureUploadAdapter.addItem(path);
                }
            } else {
                Log.e(TAG, "取消操作");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_STORAGE) {
            //无需再次检查权限，所以直接调用 selectPictures(boolean hasPermission) 方法
            pictureUploadAdapter.enableUploadBtn();
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                selectPictures(true);
            } else {
                ToastUtil.showShort(this, "请授予内置存储的读写权限后再使用本功能");
                //selectPictures(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (popupSwitchLeibie != null && popupSwitchLeibie.isShowing()) {
            popupSwitchLeibie.dismiss();
        } else {
            //判断内容是否为空
            if (tease.getContent().length() != 0 ||
                    pictureUploadAdapter.getRealPicturePaths().size() != 0) {

                Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_activity_publish_dynamic),
                        "要放弃发布动态吗？",
                        Snackbar.LENGTH_SHORT);
                snackbar.setAction("放弃", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PostTeaseActivity.super.onBackPressed();
                    }
                });
                snackbar.show();
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * 发布动态
     */
    private void publishDynamic() {
        //内容至少 15 字，照片至少 1 张
        StringBuilder errorToast = new StringBuilder("");
        if (tease.getContent().length() < 15) {
            errorToast.append("动态内容至少15字");
        }
        if (pictureUploadAdapter.getRealPicturePaths().size() < 1) {
            if (errorToast.toString().length() != 0) {
                errorToast.append("，");
            }
            errorToast.append("至少上传一张照片");
        }
        // errorBuilder 不为空就弹出 toast
        if (errorToast.toString().length() != 0) {
            ToastUtil.showLong(this, errorToast);
            return;
        }
        //开始上传照片
        List<String> filePaths = new ArrayList<>(pictureUploadAdapter.getRealPicturePaths());
        //上传照片
        UploadPicturesDialog.create(this, "上传照片").startUpload(filePaths, this);
    }

    @Override
    public void onUploadPicturesDone(List<BmobFile> files, BmobException e) {
        if (e == null) {
            //上传完成，发布动态
            tease.setPictures(new ArrayList<>(files));
            tease.save(PublishDynamicListener.create(this));
        }
    }

    @Override
    public void onPublishDone(BmobException e) {
        if (e == null) {
            //发布成功
            Intent intent = new Intent();
            intent.putExtra("Dynamic", tease);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 使用 Matisse 选择图片
     * @param hasPermission true：有权限
     *                      false：没有权限
     */
    private void selectPictures(boolean hasPermission) {
        if (hasPermission) {
            Matisse.from(this)
                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                    .theme(R.style.Matisse_Dracula)
                    .countable(true)
                    .maxSelectable(maxSelectable)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_CHOOSE_PICTURE);
            maxSelectable = 0; //重置
        }
    }

    /**
     * 先检查权限，再调用 selectPictures()
     * @param max 最大可选择数
     */
    public void selectPictures(int max) {
        maxSelectable = max;
        selectPictures(hasStoragePermission());
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
                ToastUtil.showShort(this, "需要内置存储的读写权限上传照片哦");
            }
            return false;
        } else {
            return true;
        }
    }

}
