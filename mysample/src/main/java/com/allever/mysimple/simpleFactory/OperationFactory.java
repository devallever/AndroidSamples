package com.allever.mysimple.simpleFactory;

/**
 * Created by Allever on 2016/11/12.
 */

public class OperationFactory {
    public static Operation createOperate(String operate){
        Operation  operation = null;
        switch (operate){
            case "+":
                operation = new OperationAdd(); //多态
                break;
            case "-":
                operation = new OperationReduce();
                break;
            case "*":
                operation = new OperationMultiply();
                break;
            case "/":
                operation = new OperationDivision();
                break;
        }
        return operation;
    }
}
