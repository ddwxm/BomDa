package com.demo.somnus.bomda.view.IView;

/**
 * Created by Somnus on 2018/5/7.
 * 个人信息接口
 */

public interface IPersonActivity {

    /**
     * 加载关注与被关注
     * @param focus
     * @param beFocus
     */
    void loadNum(String focus,String beFocus);

    /**
     * 关注失败
     */
    void loadFail();
}
