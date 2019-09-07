package com.allever.mysimple.observerPattern;

/**
 * Created by Allever on 2016/11/20.
 * 抽象观察者，订阅者，
 */

public abstract class Observer {
    protected String name;
    protected Subject subject;

    public Observer(String name, Subject subject){
        this.name = name;
        this.subject = subject;
    }
    public abstract void update();
}
