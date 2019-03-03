package com.demo.somnus.bomda.presenter;

import com.demo.somnus.bomda.model.bean.Focus;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IPersonPresenter;
import com.demo.somnus.bomda.view.IView.IPersonActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Somnus on 2018/5/7.
 * 个人信息Presenter
 */

public class PersonPresenter implements IPersonPresenter {
    private IPersonActivity iPersonActivity;

    public PersonPresenter(IPersonActivity iPersonActivity){
        this.iPersonActivity = iPersonActivity;
    }

    @Override
    public void getNum(final User user) {
        String beFocus = null;
        BmobQuery<Focus> focusBmobQuery = new BmobQuery<>();
        focusBmobQuery.addWhereEqualTo("focuser",user.getObjectId());
        focusBmobQuery.findObjects(new FindListener<Focus>() {
            @Override
            public void done(List<Focus> list, BmobException e) {
                if (e == null){
                    final String focus = list.size()+"";
                    BmobQuery<Focus> focusBmobQuery1 = new BmobQuery<>();
                    focusBmobQuery1.addWhereEqualTo("user",user.getObjectId());
                    focusBmobQuery1.findObjects(new FindListener<Focus>() {
                        @Override
                        public void done(List<Focus> list, BmobException e) {
                            if (e == null){
                                iPersonActivity.loadNum(focus,list.size()+"");
                            } else {
                                iPersonActivity.loadFail();
                            }
                        }
                    });
                } else {
                    iPersonActivity.loadFail();
                }
            }
        });
    }
}
