package com.allever.allsample.vodeotest.model;

/**
 * Created by allever on 17-10-11.
 */
public class Source {
    private String type = "";
    private int id = -1;
    private int duration = -1;
    private String source = "";
    private int highRpm = -1;
    private int lowRpm = -1;
    private int highSpeed = -1;
    private int lowSpeed = -1;

    private int stringId = -1;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getHighRpm() {
        return highRpm;
    }

    public void setHighRpm(int highRpm) {
        this.highRpm = highRpm;
    }

    public int getLowRpm() {
        return lowRpm;
    }

    public void setLowRpm(int lowRpm) {
        this.lowRpm = lowRpm;
    }

    public int getHighSpeed() {
        return highSpeed;
    }

    public void setHighSpeed(int highSpeed) {
        this.highSpeed = highSpeed;
    }

    public int getLowSpeed() {
        return lowSpeed;
    }

    public void setLowSpeed(int lowSpeed) {
        this.lowSpeed = lowSpeed;
    }

    public int getStringId() {
        return stringId;
    }

    public void setStringId(int stringId) {
        this.stringId = stringId;
    }
}
