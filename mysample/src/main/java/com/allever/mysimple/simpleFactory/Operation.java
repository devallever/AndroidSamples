package com.allever.mysimple.simpleFactory;

/**
 * Created by Allever on 2016/11/12.
 */


//封装
public class Operation {
    protected double number_1;
    protected double number_2;

    public double getNumber_1() {
        return number_1;
    }

    public void setNumber_1(double number_1) {
        this.number_1 = number_1;
    }

    public double getNumber_2() {
        return number_2;
    }

    public void setNumber_2(double number_2) {
        this.number_2 = number_2;
    }

    public double getResult(){
        double result = 0;
        return  result;
    }
}
