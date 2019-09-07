package com.allever.mysimple.strategyModel;

/**
 * Created by Allever on 2016/11/14.
 */

public class CashNormal extends CashSuper {
    @Override
    public double acceptCash(double money) {
        return money;
    }
}
