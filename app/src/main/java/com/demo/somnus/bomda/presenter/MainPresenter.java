package com.demo.somnus.bomda.presenter;


import com.demo.somnus.bomda.presenter.IPresenter.IMainPresenter;
import com.demo.somnus.bomda.view.IView.IMainActivity;

/**
 * Created by Somnus on 2018/4/4.
 * 主类Presenter
 */

public class MainPresenter implements IMainPresenter {


    private IMainActivity iMainActivity;

    public MainPresenter(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
    }
}
