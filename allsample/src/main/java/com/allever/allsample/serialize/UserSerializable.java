package com.allever.allsample.serialize;

import com.allever.allsample.okhttp.bean.User;

import java.io.Serializable;

/**
 * Created by allever on 17-11-4.
 */

public class UserSerializable implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    public long isbn;

    public UserSerializable(int id, String name, long isbn){
        this.id = id;
        this.name = name;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }
}
