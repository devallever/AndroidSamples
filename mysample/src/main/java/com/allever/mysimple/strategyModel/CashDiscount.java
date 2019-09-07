package com.allever.mysimple.strategyModel;

/**
 * Created by Allever on 2016/11/14.
 */

public class CashDiscount extends CashSuper {
    private double rebate = 1d;
    public CashDiscount(double rebate){
        this.rebate = rebate;
    }

    @Override
    public double acceptCash(double money) {
        return money * rebate;
    }
}
