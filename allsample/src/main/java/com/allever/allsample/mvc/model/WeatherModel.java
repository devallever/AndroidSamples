package com.allever.allsample.mvc.model;

/**
 * Created by allever on 17-10-25.
 */

public interface WeatherModel {
    void getWeather(String cityId, WeatherListener weatherListener);
}
