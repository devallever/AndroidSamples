package com.allever.mysimple.simpleFactory;

/**
 * Created by Allever on 2016/11/12.
 */

public class OperationReduce extends Operation {
    @Override
    public double getResult() {
        double result = 0;
        result = getNumber_1() - getNumber_2();
        return result;
    }
}
