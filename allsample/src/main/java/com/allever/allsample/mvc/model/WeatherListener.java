package com.allever.allsample.mvc.model;

/**
 * Created by allever on 17-10-25.
 */

public interface WeatherListener {
    /**
     * success callback
     *
     * @param  weatherResult aa
     *
     *
     * */
    void onSuccess(String weatherResult);
    void onFail(String message);
}
