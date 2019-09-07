package com.allever.mysimple.observerPattern;

/**
 * Created by Allever on 2016/11/20.
 * 抽象通知者，发布者
 */

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    String getSubjectAction();
    void setSubjectAction(String action);
}
