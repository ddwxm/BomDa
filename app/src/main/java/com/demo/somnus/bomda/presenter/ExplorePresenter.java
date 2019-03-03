package com.demo.somnus.bomda.presenter;

import com.demo.somnus.bomda.presenter.IPresenter.IExplorePresenter;
import com.demo.somnus.bomda.view.IView.IExploreFragment;

/**
 * Created by Somnus on 2018/4/12.
 * 探索Presenter
 */

public class ExplorePresenter implements IExplorePresenter {
    private IExploreFragment iExploreFragment;

    public ExplorePresenter(IExploreFragment iExploreFragment){
        this.iExploreFragment = iExploreFragment;
    }
}
