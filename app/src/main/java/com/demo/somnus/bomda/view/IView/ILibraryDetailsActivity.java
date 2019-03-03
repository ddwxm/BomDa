package com.demo.somnus.bomda.view.IView;

import com.demo.somnus.bomda.model.bean.NewsDetails;

/**
 * Created by Somnus on 2018/4/15.
 * 图书馆新闻详情接口
 */

public interface ILibraryDetailsActivity {
    /**
     * 新闻详情加载
     * @param newsDetails 新闻详情
     */
    void NewsDetailsLoadSuccess(NewsDetails newsDetails);
}
