package com.demo.somnus.bomda.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IPersonPresenter;
import com.demo.somnus.bomda.presenter.PersonPresenter;
import com.demo.somnus.bomda.util.ToastUtil;
import com.demo.somnus.bomda.view.IView.IPersonActivity;
import com.gyf.barlibrary.ImmersionBar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Somnus on 2018/4/4.
 * 个人中心Activity
 */

public class PersonActivity extends AppCompatActivity implements IPersonActivity,View.OnClickListener{
    private ImageView person_bg,person_avatar,person_sex,person_back,person_editor;
    private TextView person_nick,person_signature,person_focus_num,person_fans_num;
    private LinearLayout person_cover;
    private Toolbar toolbar;
    public enum RequestCode { SETIN, SETUT }
    private IPersonPresenter iPersonPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iPersonPresenter = new PersonPresenter(this);
        ImmersionBar.with(this).transparentBar().init();
        setContentView(R.layout.activity_person);

        initWidget();
        showInfo(BmobUser.getCurrentUser(User.class));
        initClickListener();
    }

    private void initWidget(){
        person_bg = (ImageView) findViewById(R.id.person_bg);
        person_avatar = (ImageView) findViewById(R.id.person_avatar);
        person_sex = (ImageView) findViewById(R.id.person_sex);
        person_nick = (TextView) findViewById(R.id.person_nick);
        person_signature = (TextView) findViewById(R.id.person_signature);
        person_focus_num = (TextView) findViewById(R.id.person_focus_num);
        person_fans_num = (TextView) findViewById(R.id.person_fans_num);
        person_editor = (ImageView) findViewById(R.id.person_editor);
        person_cover = (LinearLayout) findViewById(R.id.person_cover);
        person_back = (ImageView) findViewById(R.id.person_back);
        iPersonPresenter.getNum(BmobUser.getCurrentUser(User.class));
    }

    private void initClickListener(){
        person_back.setOnClickListener(this);
        person_avatar.setOnClickListener(this);
        person_bg.setOnClickListener(this);
        person_editor.setOnClickListener(this);
    }

    private void showInfo(User user){

        if (user.getQqLogin()){
            person_bg.setImageResource(R.mipmap.mine_blue_bg);

            Glide.with(this)
                    .load(user.getQqavatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_avatar_24dp)
                    .bitmapTransform(new CropCircleTransformation(this))
                    .into(person_avatar);
        } else {
            if (user.getBackground() != null){
                Glide.with(this.getApplicationContext())
                        .load(user.getBackground().getFileUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.side_nav_bar)
                        .into(person_bg);
            } else {
                person_bg.setImageResource(R.color.colorGery);
            }
            if (user.getAvatar() != null){
                Glide.with(this.getApplicationContext())
                        .load(user.getAvatar().getFileUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar_24dp)
                        .bitmapTransform(new CropCircleTransformation(this))
                        .into(person_avatar);
            } else {
                person_avatar.setImageResource(R.drawable.ic_avatar_24dp);
            }
        }
        if (!user.getNick().equals("")){
            person_nick.setText(user.getNick());
        } else {
            person_nick.setText(R.string.WeiTiXie);
        }
        switch (user.getSex()) {
            case "男":
                person_sex.setImageResource(R.drawable.ic_man_blue_24dp);
                break;
            case "女":
                person_sex.setImageResource(R.drawable.ic_woman_pink_24dp);
                break;
            default:
                person_sex.setVisibility(View.GONE);
        }
        if (!user.getIntroduction().equals("")){
            person_signature.setText(user.getIntroduction());
        } else {
            person_signature.setText(R.string.WeiTiXie);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.person_back:
                setResult(RequestCode.SETIN.ordinal());
                finish();
                break;
            case R.id.person_avatar:
                if (BmobUser.getCurrentUser(User.class).getQqLogin()){
                    Intent intent = new Intent(this, ViewImageActivity.class);
                    intent.putExtra("isQQ",true);
                    intent.putExtra("type","avatar");
                    intent.putExtra("imagePath",BmobUser.getCurrentUser(User.class).getQqavatar());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, ViewImageActivity.class);
                    intent.putExtra("isQQ",false);
                    intent.putExtra("type","avatar");
                    intent.putExtra("imagePath", BmobUser.getCurrentUser(User.class).getAvatar().getFileUrl() == null ? "" : BmobUser.getCurrentUser(User.class).getAvatar().getFileUrl());
                    startActivity(intent);
                }
                break;
            case R.id.person_bg:
                if (BmobUser.getCurrentUser(User.class).getQqLogin()){
                    Intent intent = new Intent(this, ViewImageActivity.class);
                    intent.putExtra("isQQ",true);
                    intent.putExtra("type","background");
                    intent.putExtra("imagePath","null");
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(this, ViewImageActivity.class);
                    intent1.putExtra("isQQ",false);
                    intent1.putExtra("type","background");
                    intent1.putExtra("imagePath", BmobUser.getCurrentUser(User.class).getBackground().getFileUrl() == null ? "" : BmobUser.getCurrentUser(User.class).getBackground().getFileUrl());
                    startActivity(intent1);
                }
                break;
            case R.id.person_editor:
                Intent intent2 = new Intent(this, ModifiedActivity.class);
                startActivityForResult(intent2, RequestCode.SETIN.ordinal());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.SETIN.ordinal()) {
            if (resultCode == Activity.RESULT_OK) {
                showInfo(BmobUser.getCurrentUser(User.class));
            } else {
                // do noting
            }
        }
    }

    @Override
    public void loadNum(String focus, String beFocus) {
        person_focus_num.setText(focus);
        person_fans_num.setText(beFocus);
    }

    @Override
    public void loadFail() {
        ToastUtil.showShort(PersonActivity.this,"加载关注粉丝失败");
    }
}
