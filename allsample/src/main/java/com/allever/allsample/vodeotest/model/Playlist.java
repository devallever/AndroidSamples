package com.allever.allsample.vodeotest.model;

import java.util.List;

/**
 * Created by allever on 17-10-11.
 */
public class Playlist {
    private int nameStringId;
    private int locationStringId;
    private int locationDescriptionStringId;
    private int imageSourceId;
    private List<PlayElement> playElementList;

    public int getNameStringId() {
        return nameStringId;
    }

    public void setNameStringId(int nameStringId) {
        this.nameStringId = nameStringId;
    }

    public int getLocationStringId() {
        return locationStringId;
    }

    public void setLocationStringId(int locationStringId) {
        this.locationStringId = locationStringId;
    }

    public int getLocationDescriptionStringId() {
        return locationDescriptionStringId;
    }

    public void setLocationDescriptionStringId(int locationDescriptionStringId) {
        this.locationDescriptionStringId = locationDescriptionStringId;
    }

    public int getImageSourceId() {
        return imageSourceId;
    }

    public void setImageSourceId(int imageSourceId) {
        this.imageSourceId = imageSourceId;
    }

    public List<PlayElement> getPlayElementList() {
        return playElementList;
    }

    public void setPlayElementList(List<PlayElement> playElementList) {
        this.playElementList = playElementList;
    }
}
