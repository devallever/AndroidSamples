package com.allever.allsample.vodeotest.model;

import java.util.Map;

/**
 * Created by allever on 17-10-11.
 */
public class Atom {
    private int id;
    private int videoSourceId;
    //private List<Level> levelList;
    private Map<Integer,Level> levelMap;//<levelId, Level>

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVideoSourceId() {
        return videoSourceId;
    }

    public void setVideoSourceId(int videoSourceId) {
        this.videoSourceId = videoSourceId;
    }

/*    public List<Level> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<Level> levelList) {
        this.levelList = levelList;
    }*/

    public Map<Integer, Level> getLevelMap() {
        return levelMap;
    }

    public void setLevelMap(Map<Integer, Level> levelMap) {
        this.levelMap = levelMap;
    }
}
