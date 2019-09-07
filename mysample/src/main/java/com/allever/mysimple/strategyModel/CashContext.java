package com.allever.mysimple.strategyModel;

/**
 * Created by Allever on 2016/11/14.
 */

public class CashContext {
    private CashSuper cashSuper = null;
    public CashContext(String type){
        switch (type){
            case "打8折":
                cashSuper = new CashDiscount(0.8);
                break;
            case "满300返100":
                cashSuper = new CashReturn(300,100);
                break;
            case "正常计费":
                cashSuper = new CashNormal();
                break;
        }
    }

    public double getResult(double money){
        return cashSuper.acceptCash(money);
    }
}
