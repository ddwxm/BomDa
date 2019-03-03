package com.demo.somnus.bomda.model.bean;

/**
 * Created by Somnus on 2018/2/28.
 * 二手市场Bean
 */

public class Treasure {
    private Market market;
    private Integer praise;
    private Integer sum;


    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
