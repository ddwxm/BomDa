package com.demo.somnus.bomda.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.somnus.bomda.R;
import com.demo.somnus.bomda.model.bean.InfoNum;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.view.IView.IMineFragment;
import com.demo.somnus.bomda.view.activity.CollectActivity;
import com.demo.somnus.bomda.view.activity.FocusActivity;
import com.demo.somnus.bomda.view.activity.MainActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Somnus on 2018/4/4.
 * 我的Fragment
 */

public class MineFragment extends Fragment implements IMineFragment
        ,View.OnClickListener {
    private ImageView mine_avatar,mine_sex;
    private TextView mine_nick;
    public enum RequestCode { LOGIN, LOGOUT,SITING }
    private RelativeLayout mine_login,mine_logout;
    private Toolbar mine_toolbar;
    public static  String XH;
    private View rootView;
    private Button mine_go_login;
    private TextView mine_dynamic,mine_collection,mine_focus,mine_friends,mine_buys;
    private TextView mine_nearby,mine_tool;
    private int dynamic,focus,collection,praise;
    private TextView mine_dynamic_num,mine_love_num,mine_focus_num,mine_collection_num;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initWidget(rootView);
        initClick();
        isLogin();

        return rootView;
    }

    /**
     * 是否登录
     */
    public void isLogin(){
        if (BmobUser.getCurrentUser(User.class) != null){
            showCurrentUserInfo(BmobUser.getCurrentUser(User.class));
            mine_login.setVisibility(View.VISIBLE);
            mine_logout.setVisibility(View.GONE);
        } else {
            mine_login.setVisibility(View.GONE);
            mine_logout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取数量
     * @param dynamic 动态
     * @param praise 点赞
     * @param collection 收藏
     * @param focus 关注
     */
    public void getNum(int dynamic,int praise,int collection,int focus){
        this.dynamic = dynamic;
        this.praise = praise;
        this.focus = focus;
        this.collection = collection;
    }

    /**
     * 加载个人信息
     * @param currentUser
     */
    public void showCurrentUserInfo(User currentUser){
        if (currentUser != null) {
            mine_login.setVisibility(View.VISIBLE);
            mine_logout.setVisibility(View.GONE);
            if (currentUser.getQqLogin()){
                // 加载昵称
                String nick = currentUser.getNick();
                if (nick != null){
                    mine_nick.setText(currentUser.getNick());
                } else {
                    mine_nick.setText(R.string.WeiTiXie);
                }

                Glide.with(this.getContext())
                        .load(currentUser.getQqavatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_avatar_24dp)
                        .bitmapTransform(new CropCircleTransformation(getContext()))
                        .into(mine_avatar);
            } else {
                // 加载昵称
                String nick = currentUser.getNick();
                if (nick != null){
                    mine_nick.setText(currentUser.getNick());
                } else {
                    mine_nick.setText(R.string.WeiTiXie);
                }

                // 加载头像
                BmobFile avatar = currentUser.getAvatar();
                if (avatar != null) {
                    Glide.with(this.getContext())
                            .load(avatar.getFileUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_avatar_24dp)
                            .bitmapTransform(new CropCircleTransformation(getContext()))
                            .into(mine_avatar);
                }

                // 加载性别
                String sex = currentUser.getSex();
                if (sex != null){
                    switch (sex){
                        case "男":
                            mine_sex.setImageResource(R.drawable.ic_man_blue_24dp);
                            break;
                        case "女":
                            mine_sex.setImageResource(R.drawable.ic_woman_pink_24dp);
                            break;
                    }
                } else {
                    mine_sex.setVisibility(View.GONE);
                }
                mine_dynamic_num.setText(String.valueOf(dynamic));
                mine_collection_num.setText(String.valueOf(collection));
                mine_love_num.setText(String.valueOf(praise));
                mine_focus_num.setText(String.valueOf(focus));
            }
        } else {
            mine_login.setVisibility(View.GONE);
            mine_logout.setVisibility(View.VISIBLE);
            mine_dynamic_num.setText("0");
            mine_collection_num.setText("0");
            mine_love_num.setText("0");
            mine_focus_num.setText("0");
        }
    }

    @Override
    public void loadNum(InfoNum infoNum) {
        mine_dynamic_num.setText(String.valueOf(infoNum.getDynamicNum()));
        mine_collection_num.setText(String.valueOf(infoNum.getCollectionNum()));
        mine_love_num.setText(String.valueOf(infoNum.getPraiseNum()));
        mine_focus_num.setText(String.valueOf(infoNum.getFocusNum()));
    }

    /**
     * 初始化控件
     * @param view
     */
    private void initWidget(View view){
        mine_toolbar = (Toolbar) view.findViewById(R.id.mine_toolbar);
        mine_avatar = (ImageView) view.findViewById(R.id.mine_avatar);
        mine_sex = (ImageView) view.findViewById(R.id.mine_sex);

        mine_nick = (TextView) view.findViewById(R.id.mine_nick);

        mine_login = (RelativeLayout) view.findViewById(R.id.mine_isLogin);
        mine_logout = (RelativeLayout) view.findViewById(R.id.mine_noLogin);

        mine_go_login = (Button) view.findViewById(R.id.mine_go_login);

        mine_dynamic = (TextView) view.findViewById(R.id.mine_dynamic);
        mine_collection = (TextView) view.findViewById(R.id.mine_collection);
        mine_focus = (TextView) view.findViewById(R.id.mine_focus);
        mine_friends = (TextView) view.findViewById(R.id.mine_friends);
        mine_buys = (TextView) view.findViewById(R.id.mine_buys);
        mine_nearby = (TextView) view.findViewById(R.id.mine_nearby);
        mine_tool = (TextView) view.findViewById(R.id.mine_tool);

        mine_dynamic_num = (TextView) view.findViewById(R.id.mine_dynamic_num);
        mine_love_num = (TextView) view.findViewById(R.id.mine_love_num);
        mine_focus_num = (TextView) view.findViewById(R.id.mine_focus_num);
        mine_collection_num = (TextView) view.findViewById(R.id.mine_collection_num);
    }

    /**
     * 初始化控件点击事件
     */
    private void initClick(){
        mine_login.setOnClickListener(this);
        mine_tool.setOnClickListener(this);
        mine_go_login.setOnClickListener(this);
        mine_collection.setOnClickListener(this);
        mine_focus.setOnClickListener(this);
    }


    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_go_login:
                ((MainActivity) getActivity()).login();
                break;
            case R.id.mine_isLogin:
                ((MainActivity) getActivity()).info(BmobUser.getCurrentUser(User.class));
                break;
            case R.id.mine_tool:
                ((MainActivity) getActivity()).settings();
                break;
            case R.id.mine_collection:
                Intent intent = new Intent(getContext(), CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_focus:
                Intent intent_focus = new Intent(getContext(), FocusActivity.class);
                startActivity(intent_focus);
                break;
        }
    }
}
