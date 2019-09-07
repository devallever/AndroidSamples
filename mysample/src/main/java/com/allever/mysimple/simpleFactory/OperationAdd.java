package com.allever.mysimple.simpleFactory;

/**
 * Created by Allever on 2016/11/12.
 */

//继承
public class OperationAdd extends Operation {
    @Override
    public double getResult() {
        double result = 0;
        result = number_1 + number_2;
        return result;
    }
}
