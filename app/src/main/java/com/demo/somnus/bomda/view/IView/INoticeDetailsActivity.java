package com.demo.somnus.bomda.view.IView;


import com.demo.somnus.bomda.model.bean.AnnouncementDetails;

/**
 * Created by Somnus on 2018/4/5.
 * 校园公告详情接口
 */

public interface INoticeDetailsActivity {

    /**
     * 加载公告详情
     * @param announcementDetails
     */
    void NoticeDetailsLoadSuccess(AnnouncementDetails announcementDetails);
}
