package com.allever.allsample.vodeotest.model;

/**
 * Created by allever on 17-10-11.
 */
public class Event {
    private int time;
    private int resistance;
    private float incline;

    private EventSource eventSource;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public float getIncline() {
        return incline;
    }

    public void setIncline(float incline) {
        this.incline = incline;
    }

    public EventSource getEventSource() {
        return eventSource;
    }

    public void setEventSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }
}
