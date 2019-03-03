package com.demo.somnus.bomda.presenter;

import android.content.Context;

import com.demo.somnus.bomda.model.bean.Blacklist;
import com.demo.somnus.bomda.model.bean.QQInfo;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IAccountPresenter;
import com.demo.somnus.bomda.view.IView.IAccountActivity;
import com.google.gson.Gson;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Somnus on 2018/4/10.
 * 账号管理Presenter
 */

public class AccountPresenter implements IAccountPresenter {
    private IAccountActivity iAccountActivity;

    public AccountPresenter(IAccountActivity iAccountActivity){
        this.iAccountActivity = iAccountActivity;
    }

    @Override
    public void search(Context context, User founder) {
        BmobQuery<Blacklist> query = new BmobQuery<>();
        query.addWhereEqualTo("founder",founder.getObjectId());
        query.include("object");
        query.findObjects(new FindListener<Blacklist>() {
            @Override
            public void done(List<Blacklist> list, BmobException e) {
                if (e == null){
                    if (list.size() != 0){
                        iAccountActivity.onLoadBlacklistListSuccess(list);
                    } else {
                        iAccountActivity.sendNull(list.size());
                    }
                } else {
                    iAccountActivity.refreshFail();
                }
            }
        });
    }

    @Override
    public void qqBinding(QQInfo qqInfo) {
        User user = new User();
        user.setQqBinding(true);
        user.setQqLogin(false);
        user.setQqId(qqInfo.getId());
        user.setQqavatar(qqInfo.getAvatar());
        user.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    User.fetchUserJsonInfo(new FetchUserInfoListener<String>() { //更新本地用户信息
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null){
                                Gson gson = new Gson();
                                User user = gson.fromJson(s,User.class);
                                User user1 = BmobUser.getCurrentUser(User.class);
                                user1.setEmail(user.getEmail());
                                user1.setNick(user.getNick());
                                user1.setAvatar(user.getAvatar());
                                user1.setSex(user.getSex());
                                iAccountActivity.BindingSuccess(user1,"QQ");
                            } else {
                                iAccountActivity.BindingFail(e.getMessage(),e.getErrorCode());
                            }
                        }
                    });
                } else {
                    iAccountActivity.BindingFail(e.getMessage(),e.getErrorCode());
                }
            }
        });
    }
}
