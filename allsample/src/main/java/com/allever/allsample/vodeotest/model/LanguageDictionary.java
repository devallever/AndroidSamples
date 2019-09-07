package com.allever.allsample.vodeotest.model;

import java.util.Map;

/**
 * Created by allever on 17-10-11.
 */
public class LanguageDictionary {
    private Map<Integer, DescString> descStringMap; //<stringId, DescString>

    public Map<Integer, DescString> getDescStringMap() {
        return descStringMap;
    }

    public void setDescStringMap(Map<Integer, DescString> descStringMap) {
        this.descStringMap = descStringMap;
    }
}
