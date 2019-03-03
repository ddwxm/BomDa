package com.demo.somnus.bomda.view.IView;

import com.demo.somnus.bomda.model.bean.Grade;
import com.demo.somnus.bomda.model.bean.GradeParent;

import java.util.List;
import java.util.Map;

/**
 * Created by Somnus on 2018/4/4.
 * 成绩接口
 */

public interface IGradeActivity {

    /**
     * 第一次读取获取成绩失败
     * @param msg
     * @param code
     */
    void tryFail(String msg,int code);

    /**
     * 查询失败回调
     */
    void getFail(String msg,int code);

    /**
     * 加载成绩列表
     * @param parent
     */
    void loadGradeList(List<Grade> parent);

    /**
     * 查询失败
     * @param msg
     * @param code
     */
    void queryFail(String msg,int code);

    void noCJ();
}
