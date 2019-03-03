package com.demo.somnus.bomda.presenter;

import android.util.Log;

import com.demo.somnus.bomda.model.bean.Focus;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IFocusPresenter;
import com.demo.somnus.bomda.view.IView.IFocusActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Somnus on 2018/4/15.
 *
 */

public class FocusPresenter implements IFocusPresenter {

    private IFocusActivity iFocusActivity;

    public FocusPresenter(IFocusActivity iFocusActivity){
        this.iFocusActivity = iFocusActivity;
    }

    @Override
    public void queryFocus(User user) {
        BmobQuery<Focus> focusBmobQuery = new BmobQuery<>();
        focusBmobQuery.addWhereEqualTo("focuser",user);
        focusBmobQuery.include("user,focuser");
        focusBmobQuery.order("-createdAt");
        focusBmobQuery.findObjects(new FindListener<Focus>() {
            @Override
            public void done(List<Focus> list, BmobException e) {
                if (e == null){
                    if (list.size() != 0){
                        iFocusActivity.loadFocusListSuccess(list);
                        iFocusActivity.refreshSuccess();
                        Log.e("size",list.size()+"ww");
                    } else {
                        Log.e("size",list.size()+"");
                        iFocusActivity.noFocus();
                        iFocusActivity.refreshSuccess();
                    }
                } else {
                    iFocusActivity.loadFocusListFail(e.getMessage(),e.getErrorCode());
                }
            }
        });
    }
}
