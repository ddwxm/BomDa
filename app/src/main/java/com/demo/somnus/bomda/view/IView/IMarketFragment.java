package com.demo.somnus.bomda.view.IView;

import com.demo.somnus.bomda.model.bean.Treasure;

import java.util.List;

/**
 * Created by Somnus on 2018/4/12.
 * 二手市场Fragment接口
 */

public interface IMarketFragment {

    /**
     * 加载好评东西list
     * @param treasureList
     */
    void loadSiftMarketList(List<Treasure> treasureList);
}
