package com.demo.somnus.bomda.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.somnus.bomda.model.bean.Activities;
import com.demo.somnus.bomda.model.bean.Shuffling;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IHomePresenter;
import com.demo.somnus.bomda.view.IView.IHomeFragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Somnus on 2018/4/4.
 * 主页Presenter
 */

public class HomePresenter implements IHomePresenter {
    private IHomeFragment iHomeFragment;

    public HomePresenter(IHomeFragment iHomeFragment){
        this.iHomeFragment = iHomeFragment;
    }

    @Override
    public void search_shuffling(final Context context) {
        BmobQuery<Shuffling> query = new BmobQuery<Shuffling>();
        query.findObjects(new FindListener<Shuffling>() {
            @Override
            public void done(List<Shuffling> list, BmobException e) {
                if (e == null){
                    if (list.size() != 0){
                        iHomeFragment.onLoadShufflingListSuccess(list);
                        Log.e("加载图片列表", "个数：(" + list.size() + ")");
                    } else {
                        Log.e("加载图片列表", "个数：(" + list.size() + ")");
                    }
                } else {
                    Log.e("加载图片列表", "失败：(" + e.getErrorCode() + ")" + e.getMessage());
                    Toast.makeText(context, "(" + e.getErrorCode() + ")" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void search_activity(final Context context) {
        BmobQuery<Activities> query = new BmobQuery<>();
        query.findObjects(new FindListener<Activities>() {
            @Override
            public void done(List<Activities> list, BmobException e) {
                if (e == null){
                    if (list.size() != 0){
                        iHomeFragment.onLoadActivityListSuccess(list);
                        Log.e("加载图片列表", "个数：(" + list.size() + ")");
                    } else {
                        Log.e("加载图片列表", "个数：(" + list.size() + ")");
                    }
                } else {
                    Log.e("加载图片列表", "失败：(" + e.getErrorCode() + ")" + e.getMessage());
                    Toast.makeText(context, "(" + e.getErrorCode() + ")" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void search_homing(Context context) {

    }

    @Override
    public void isBinding(Context context, User user, final int code) {
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(user.getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    if (user.getBinding()){
                        iHomeFragment.sendInfo(user.getStudentId(),user.getStudentPassword(),code);
                    } else {
                        iHomeFragment.notBinding();
                    }
                } else {
                    Log.e("error",e.getMessage()+e.getErrorCode());
                }
            }
        });
    }
}
