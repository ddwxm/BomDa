package com.demo.somnus.bomda.view.IView;


import com.demo.somnus.bomda.model.bean.Announcement;

import java.util.List;

/**
 * Created by Somnus on 2018/4/4.
 * 校园公告接口
 */

public interface ISchoolNoticeActivity {

    /**
     * 校内公告
     * @param announcementList 公告列表
     */
    void onLoadNoticeListSuccess(List<Announcement> announcementList);

    /**
     * 刷新成功
     */
    void RefreshSuccess();
}
