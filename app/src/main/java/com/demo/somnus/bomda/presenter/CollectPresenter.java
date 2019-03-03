package com.demo.somnus.bomda.presenter;

import com.demo.somnus.bomda.presenter.IPresenter.ICollectPresenter;
import com.demo.somnus.bomda.view.IView.ICollectActivity;

/**
 * Created by Somnus on 2018/4/11.
 * 收藏Presenter
 */

public class CollectPresenter implements ICollectPresenter {
    private ICollectActivity iCollectActivity;

    public CollectPresenter(ICollectActivity iCollectActivity){
        this.iCollectActivity = iCollectActivity;
    }


}
