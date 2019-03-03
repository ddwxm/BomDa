package com.demo.somnus.bomda.view.IView;

import com.demo.somnus.bomda.model.bean.TeaseSpecific;

import java.util.List;

/**
 * Created by Somnus on 2018/4/12.
 * 树洞Fragment接口
 */

public interface ITeaseFragment {

    /**
     * 加载树洞(解析后TeaseSpecific)
     * @param teaseSpecifics
     */
    void loadTeaseList(List<TeaseSpecific> teaseSpecifics);

}
