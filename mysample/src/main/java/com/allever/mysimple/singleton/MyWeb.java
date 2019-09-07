package com.allever.mysimple.singleton;

/**
 * Created by Allever on 2016/11/24.
 */

public class MyWeb {
    private MyWeb(){}

    public static MyWeb getInstance(){
        return WebHolder.web;
    }

    private static class WebHolder{
        private static final MyWeb web = new MyWeb();
    }
}
