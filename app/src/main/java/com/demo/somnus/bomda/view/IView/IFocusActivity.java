package com.demo.somnus.bomda.view.IView;

import com.demo.somnus.bomda.model.bean.Focus;

import java.util.List;

/**
 * Created by Somnus on 2018/4/15.
 * 关注接口
 */

public interface IFocusActivity {

    /**
     * 加载关注List
     * @param focusList
     */
    void loadFocusListSuccess(List<Focus> focusList);

    /**
     * 加载失败
     * @param msg
     * @param code
     */
    void loadFocusListFail(String msg,int code);

    /**
     * 没有关注
     */
    void noFocus();

    /**
     * 刷新成功
     */
    void refreshSuccess();
}
