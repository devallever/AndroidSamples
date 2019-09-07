package com.allever.mysimple.retrofit.been;

/**
 * Created by Allever on 2016/12/9.
 */

public class ModifyHeadBeen {
    private boolean seccess;
    private String message;
    private String head_path;

    public boolean isSeccess() {
        return seccess;
    }

    public void setSeccess(boolean seccess) {
        this.seccess = seccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHead_path() {
        return head_path;
    }

    public void setHead_path(String head_path) {
        this.head_path = head_path;
    }
}
