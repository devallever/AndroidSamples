package com.allever.allsample.vodeotest.model;

import java.util.List;
import java.util.Map;

/**
 * Created by allever on 17-10-11.
 */
public class Level {
    private int id;
    //private List<Event> eventList;
    private Map<Integer, Event> eventMap; //<eventId, Event>

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

/*    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }*/

    public Map<Integer, Event> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<Integer, Event> eventMap) {
        this.eventMap = eventMap;
    }
}
