package com.allever.allsample.vodeotest.model;

import java.util.Map;

/**
 * Created by allever on 17-10-12.
 */
public class DescString {
    private int id = -1;
    private String type = "";
    private Map<LanguageType,String> descMap;//<类型,具体描述>

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<LanguageType, String> getDescMap() {
        return descMap;
    }

    public void setDescMap(Map<LanguageType, String> descMap) {
        this.descMap = descMap;
    }
}
