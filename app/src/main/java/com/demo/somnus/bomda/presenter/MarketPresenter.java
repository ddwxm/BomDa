package com.demo.somnus.bomda.presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.util.Log;

import com.demo.somnus.bomda.model.bean.Market;
import com.demo.somnus.bomda.model.bean.MarketComment;
import com.demo.somnus.bomda.model.bean.TeaseSpecific;
import com.demo.somnus.bomda.model.bean.Treasure;
import com.demo.somnus.bomda.presenter.IPresenter.IMarketPresenter;
import com.demo.somnus.bomda.util.ConstantsUtil;
import com.demo.somnus.bomda.view.IView.IMarketFragment;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Somnus on 2018/5/7.
 * 跳骚市场Presenter
 */

public class MarketPresenter implements IMarketPresenter {
    private IMarketFragment iMarketFragment;
    private List<Treasure> treasures = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 2:
                    Treasure treasure = (Treasure) msg.obj;
                    Log.e("Treasure",treasure.getMarket().getObjectId());
                    treasures.add(treasure);
                    loadSiftMarketList(treasures);
                    break;
            }
        }
    };

    public MarketPresenter(IMarketFragment iMarketFragment){
        this.iMarketFragment = iMarketFragment;
    }

    @Override
    public void queryMarket() {
        final List<String> id = new ArrayList<>();
        final BmobQuery<MarketComment> query = new BmobQuery<MarketComment>();
        query.order("-createdAt");
        query.include("market,author");
        query.findObjects(new FindListener<MarketComment>() {
            @Override
            public void done(List<MarketComment> list, BmobException e) {
                if (e == null){
                    for(MarketComment marketComment:list){
                        id.add(marketComment.getMarket().getObjectId());
                    }
                    for (String s : removeDuplicate(id)){
                        Log.e("MarketObjectId",s);
                        Sum(s);
                    }
                } else {
                    Log.e(ConstantsUtil.TAG_ERROR,e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 去除List(String)中重复数据
     * @param list
     * @return
     */
    private List<String> removeDuplicate(List<String> list){
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    /**
     * 查询评论总数
     * @param ObjectId
     */
    private void Sum(final String ObjectId){
        final Treasure treasure = new Treasure();
        BmobQuery<MarketComment> query = new BmobQuery<MarketComment>();
        query.addWhereEqualTo("market",ObjectId);
        query.count(MarketComment.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null){
                    Log.e("Integer",String.valueOf(integer));
                    treasure.setSum(integer);
                    praise(ObjectId,treasure);
                } else {
                    Log.e(ConstantsUtil.TAG_ERROR,e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询好评
     * @param evaluation
     * @param objectId
     * @param treasure
     */
    private void praise(final String objectId, final Treasure treasure ){
        BmobQuery<MarketComment> query = new BmobQuery<MarketComment>();
        query.addWhereEqualTo("market",objectId);
        query.addWhereEqualTo("evaluation","好");
        query.count(MarketComment.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null){
                    treasure.setPraise(integer);
                    query_market(objectId,treasure);
                } else {
                    Log.e(ConstantsUtil.TAG_ERROR,e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询市场
     * @param objectId
     * @param treasure
     */
    private void query_market(String objectId, final Treasure treasure ){
        BmobQuery<Market> query = new BmobQuery<Market>();
        query.getObject(objectId, new QueryListener<Market>() {
            @Override
            public void done(Market market, BmobException e) {
                if (e == null){
                    treasure.setMarket(market);
                    Message msg =Message.obtain();
                    msg.obj = treasure;
                    msg.what = 2;   //标志消息的标志
                    handler.sendMessage(msg);
                } else {
                    Log.e(ConstantsUtil.TAG_ERROR,e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    /**
     * 传递解析后市场List
     * @param treasureList
     */
    private void loadSiftMarketList(List<Treasure> treasureList){
        iMarketFragment.loadSiftMarketList(treasureList);
    }
}
