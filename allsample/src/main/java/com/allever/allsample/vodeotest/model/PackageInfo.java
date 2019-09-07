package com.allever.allsample.vodeotest.model;

/**
 * Created by allever on 17-10-11.
 */
public class PackageInfo {
    private float version;
    private String title;
    private boolean isFlat;
    private boolean isCoached;

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFlat() {
        return isFlat;
    }

    public void setFlat(boolean flat) {
        isFlat = flat;
    }

    public boolean isCoached() {
        return isCoached;
    }

    public void setCoached(boolean coached) {
        isCoached = coached;
    }
}
