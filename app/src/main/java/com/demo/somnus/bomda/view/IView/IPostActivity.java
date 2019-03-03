package com.demo.somnus.bomda.view.IView;

import com.demo.somnus.bomda.model.bean.Tease;

import java.util.List;

/**
 * Created by Somnus on 2018/5/12.
 * 我的发布Activity
 */

public interface IPostActivity {

    /**
     * 加载数据
     * @param teases
     */
    void loadPostList(List<Tease> teases);

    /**
     * 没有数据
     */
    void noData();
}
