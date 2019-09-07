package com.allever.allsample.mvc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;
import com.allever.allsample.mvc.model.WeatherListener;
import com.allever.allsample.mvc.model.WeatherModel;
import com.allever.allsample.mvc.model.WeatherModelImpl;

/**
 * Created by allever on 17-10-25.
 */

public class WeatherActivity extends BaseActivity implements WeatherListener,View.OnClickListener{
    private TextView tv_result;
    private Button btn_get_weather;
    private WeatherModel mWeatherModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc_weather);

        initView();

        initData();

    }

    private void initData(){
        mWeatherModel = new WeatherModelImpl();
    }

    private void initView(){
        btn_get_weather = (Button)findViewById(R.id.id_mvc_weather_btn_get_weather);
        tv_result = (TextView)findViewById(R.id.id_mvc_weather_tv_result);
        btn_get_weather.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.id_mvc_weather_btn_get_weather) {
            mWeatherModel.getWeather("",this);
        }
    }

    @Override
    public void onSuccess(String weatherResult) {
        tv_result.setText(weatherResult);
    }


    @Override
    public void onFail(String message) {
        tv_result.setText(message);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, WeatherActivity.class);
        context.startActivity(intent);
    }
}
