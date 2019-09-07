package com.allever.mysimple.observerPattern;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2016/11/20.
 * 具体通知者，被观察者，发布者：老板
 */

public class Boss implements Subject {
    private String action;
    private List<Observer> list_observers = new ArrayList<>();

    public void notification(){
        for (Observer observer: list_observers){
            if (observer!=null) observer.update();
        }
    }

    @Override
    public void attach(Observer observer) {
        list_observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        list_observers.remove(observer);
    }

    @Override
    public void setSubjectAction(String action) {
        this.action = action;
    }

    @Override
    public String getSubjectAction() {
        return this.action;
    }
}
