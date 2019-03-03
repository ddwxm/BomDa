package com.demo.somnus.bomda.view.IView;


import com.demo.somnus.bomda.model.bean.Blacklist;
import com.demo.somnus.bomda.model.bean.User;

import java.util.List;

/**
 * Created by Somnus on 2018/4/4.
 * 账号管理接口
 */

public interface IAccountActivity {

    /**
     * 刷新成功
     */
    void refreshSuccess();

    /**
     * 刷新失败
     */
    void refreshFail();

    /**
     * 加载黑名单
     * @param blacklistList 黑名单
     */
    void onLoadBlacklistListSuccess(List<Blacklist> blacklistList);

    /**
     * 黑名单空列表
     * @param size 空
     */
    void sendNull(Integer size);

    /**
     * 绑定成功
     * @param user 返回绑定成功的User
     * @param type 绑定类别  QQ、微信、微博
     */
    void BindingSuccess(User user,String type);

    /**
     * 绑定失败
     * @param msg
     * @param code
     */
    void BindingFail(String msg,int code);
}
