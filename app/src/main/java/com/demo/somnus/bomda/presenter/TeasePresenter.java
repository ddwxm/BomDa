package com.demo.somnus.bomda.presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.demo.somnus.bomda.model.bean.Tease;
import com.demo.somnus.bomda.model.bean.TeaseComment;
import com.demo.somnus.bomda.model.bean.TeaseSpecific;
import com.demo.somnus.bomda.model.bean.User;
import com.demo.somnus.bomda.presenter.IPresenter.ITeasePresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.view.IView.ITeaseFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Somnus on 2018/4/15.
 * 树洞Presenter
 */

public class TeasePresenter implements ITeasePresenter {
    private ITeaseFragment iTeaseFragment;
    private List<TeaseSpecific> teaseSpecifics = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
                    TeaseSpecific teaseSpecific = (TeaseSpecific) msg.obj;
                    teaseSpecifics.add(teaseSpecific);
                    iTeaseFragment.loadTeaseList(teaseSpecifics);
                    break;
            }
        }
    };

    public TeasePresenter(ITeaseFragment iTeaseFragment){
        this.iTeaseFragment = iTeaseFragment;
    }

    @Override
    public void query_Tease() {
        final BmobQuery<Tease> query = new BmobQuery<Tease>();
        query.order("-createdAt");
        query.include("author");
        query.findObjects(new FindListener<Tease>() {
            @Override
            public void done(List<Tease> list, BmobException e) {
                if (e == null){
                    Log.e("Tease"+ ConstantsUtil.TAG_SIZE, list.size()+"");
                    for (Tease tease : list){
                        query_Comment(tease);
                        Log.e("Tease",tease.getContent());
                    }
                } else {
                    Log.e(ConstantsUtil.TAG_ERROR,e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询树洞的评论
     * @param tease
     */
    private void query_Comment(final Tease tease) {
        final TeaseSpecific teaseSpecific = new TeaseSpecific();
        BmobQuery<TeaseComment> query = new BmobQuery<TeaseComment>();
        query.addWhereEqualTo("tease",tease.getObjectId());
        query.findObjects(new FindListener<TeaseComment>() {
            @Override
            public void done(List<TeaseComment> list, BmobException e) {
                if (e == null){
                    //Log.e("Comment"+Constants.TAG_SIZE, list.size()+"");
                    teaseSpecific.setTease(tease);
                    teaseSpecific.setTeaseComment(list);
                    teaseSpecific.setComment(list.size());
                    query_Collection(tease,teaseSpecific);
                } else {
                    Log.e(ConstantsUtil.TAG_ERROR,e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询树洞的收藏
     * @param tease
     */
    private void query_Collection(final Tease tease, final TeaseSpecific teaseSpecific) {
        BmobQuery<User> query = new BmobQuery<User>();
        //collection是Tease表中的字段，用来存储所有收藏该帖子的用户
        query.addWhereRelatedTo("collection", new BmobPointer(tease));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null){
                    //Log.e("Collection"+Constants.TAG_SIZE, list.size()+"");
                    teaseSpecific.setCollection(list.size());
                    query_Praise(tease,teaseSpecific);
                } else {
                    Log.e(ConstantsUtil.TAG_ERROR,e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询树洞的赞
     * @param tease
     */
    private void query_Praise(Tease tease, final TeaseSpecific teaseSpecific) {
        BmobQuery<User> query = new BmobQuery<User>();
        //praise是Tease表中的字段，用来存储所有点赞该帖子的用户
        query.addWhereRelatedTo("praise", new BmobPointer(tease));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null){
                    //Log.e("Praise"+Constants.TAG_SIZE, list.size()+"");
                    teaseSpecific.setPraise(list.size());
                    Message msg =Message.obtain();
                    msg.obj = teaseSpecific;
                    msg.what=1;   //标志消息的标志
                    handler.sendMessage(msg);
                } else {
                    Log.e(ConstantsUtil.TAG_ERROR,e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void like(Tease tease) {

    }
}
