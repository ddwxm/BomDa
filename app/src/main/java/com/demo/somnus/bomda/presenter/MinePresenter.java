package com.demo.somnus.bomda.presenter;

import android.util.Log;

import com.demo.somnus.bomda.model.bean.Collection;
import com.demo.somnus.bomda.model.bean.Focus;
import com.demo.somnus.bomda.model.bean.InfoNum;
import com.demo.somnus.bomda.model.bean.Tease;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.IMinePresenter;
import com.demo.somnus.bomda.view.IView.IMineFragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Somnus on 2018/4/15.
 * 我的Presenter
 */

public class MinePresenter implements IMinePresenter {

    private IMineFragment iMineFragment;

    public MinePresenter(IMineFragment iMineFragment){
        this.iMineFragment = iMineFragment;
    }

    @Override
    public void getNum(final User user) {
        final InfoNum infoNum = new InfoNum();
        BmobQuery<Tease> teaseBmobQuery = new BmobQuery<>();
        teaseBmobQuery.addWhereEqualTo("author",user.getObjectId());
        teaseBmobQuery.findObjects(new FindListener<Tease>() {
            @Override
            public void done(List<Tease> list, BmobException e) {
                if (e == null){
                    infoNum.setDynamicNum(list.size());
                    BmobQuery<User> userBmobQuery = new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("objectId",user.getObjectId());
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null){
                                infoNum.setPraiseNum(list.get(0).getPraise());
                                BmobQuery<Collection> collectionBmobQuery = new BmobQuery<>();
                                collectionBmobQuery.addWhereEqualTo("author",user.getObjectId());
                                collectionBmobQuery.findObjects(new FindListener<Collection>() {
                                    @Override
                                    public void done(List<Collection> list, BmobException e) {
                                        if (e == null){
                                            infoNum.setCollectionNum(list.size());
                                            BmobQuery<Focus> focusBmobQuery = new BmobQuery<>();
                                            focusBmobQuery.addWhereEqualTo("focuser",user.getObjectId());
                                            focusBmobQuery.findObjects(new FindListener<Focus>() {
                                                @Override
                                                public void done(List<Focus> list, BmobException e) {
                                                    if (e == null){
                                                        infoNum.setFocusNum(list.size());
                                                        Log.e("num",infoNum.getDynamicNum()+
                                                                infoNum.getCollectionNum()+infoNum.getFocusNum()+
                                                                infoNum.getPraiseNum()+"");
                                                        iMineFragment.loadNum(infoNum);
                                                    } else {

                                                    }
                                                }
                                            });
                                        } else{

                                        }
                                    }
                                });
                            } else {

                            }
                        }
                    });
                } else {

                }
            }
        });
    }
}
