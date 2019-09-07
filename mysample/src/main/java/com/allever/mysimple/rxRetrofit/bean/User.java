package com.allever.mysimple.rxRetrofit.bean;

/**
 * Created by Allever on 2017/1/15.
 */

public class User {

    private Data user;

    public Data getUser() {
        return user;
    }

    public void setUser(Data user) {
        this.user = user;
    }

    public class Data{
        private String signature;

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
