package com.allever.allsample.mvc.model;

/**
 * Created by allever on 17-10-25.
 */

public class WeatherModelImpl implements WeatherModel {

    @Override
    public void getWeather(String cityId, WeatherListener weatherListener) {
        //模拟网络请求
        if ("".equalsIgnoreCase(cityId)){
            //
            if (weatherListener != null){
                weatherListener.onFail("please input cityId");
            }
        }else {
            //
            if (weatherListener != null){
                weatherListener.onSuccess("return weather json string");
            }
        }
    }
}
