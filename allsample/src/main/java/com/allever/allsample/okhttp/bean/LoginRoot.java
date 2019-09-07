package com.allever.allsample.okhttp.bean;

/**
 * Created by allever on 17-6-29.
 */

public class LoginRoot {
    private boolean seccess;
    private String message;
    private String session_id;
    private User user;

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

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
