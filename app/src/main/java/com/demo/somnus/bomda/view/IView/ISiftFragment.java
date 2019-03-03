package com.demo.somnus.bomda.view.IView;

import com.demo.somnus.bomda.model.bean.TeaseSpecific;

import java.util.List;

/**
 * Created by Somnus on 2018/4/12.
 * 精选Fragment接口
 */

public interface ISiftFragment {

    /**
     * 加载精选树洞(解析后TeaseSpecific)
     * @param teaseSpecifics
     */
    void loadSiftTeaseList(List<TeaseSpecific> teaseSpecifics);
}
