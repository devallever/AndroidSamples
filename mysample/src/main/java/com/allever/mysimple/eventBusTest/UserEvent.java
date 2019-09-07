package com.allever.mysimple.eventBusTest;

/**
 * Created by Allever on 2017/1/18.
 */

public class UserEvent {
    private String message;
    public UserEvent(String message){
        this.message  = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
