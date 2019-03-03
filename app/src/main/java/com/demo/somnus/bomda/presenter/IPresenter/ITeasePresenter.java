package com.demo.somnus.bomda.presenter.IPresenter;

import com.demo.somnus.bomda.model.bean.Tease;

/**
 * Created by Somnus on 2018/4/15.
 * 树洞Presenter接口
 */

public interface ITeasePresenter {


    /**
     * 查询树洞
     */
    void query_Tease();

    void like(Tease tease);
}
