package com.allever.mysimple.strategyModel;

/**
 * Created by Allever on 2016/11/14.
 */

public class CashReturn extends CashSuper{
    private double cashCondiction = 0;
    private double cashReturn = 0;

    public CashReturn(double cashCondiction, double cashReturn){
        this.cashCondiction = cashCondiction;
        this.cashReturn = cashReturn;
    }

    @Override
    public double acceptCash(double money) {
        double result = money;
        if (money > cashCondiction){
            result = money - money / cashCondiction * cashReturn;
            return result;
        }
        return result;
    }
}
