package com.demo.somnus.bomda.view.IView;


import com.demo.somnus.bomda.model.bean.News;

import java.util.List;

/**
 * Created by Somnus on 2018/4/4.
 * 学校新闻接口
 */

public interface ISchoolNewsActivity {

    /**
     * 新闻列表
     * @param newsList 新闻列表
     */
    void onLoadNewsListSuccess(List<News> newsList);

    /**
     * 刷新成功
     */
    void RefreshSuccess();
}
