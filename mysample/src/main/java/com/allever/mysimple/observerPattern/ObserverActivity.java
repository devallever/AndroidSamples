package com.allever.mysimple.observerPattern;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

/**
 * Created by Allever on 2016/11/20.
 */

public class ObserverActivity extends BaseActivity implements View.OnClickListener {

    private CheckBox cb_stock;
    private CheckBox cb_nba;
    private CheckBox cb_tv;

    private Button btn_boss;
    private Button btn_secretary;

    private Boss boss;
    private Secretary secretary;

    private StockObserver stockObserver;
    private NBAObserver nbaObserver;
    private TVObserver tvObserver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observer_activity_layout);

        setToolbar((Toolbar)findViewById(R.id.id_toolbar),"老板来啦");

        initView();

        initData();

        initEvent();

    }

    private void initData(){
        //boss = new Boss();
        //secretary = new Secretary();

        //stockObserver = new StockObserver("XM", )
    }

    private void initView(){
        cb_stock= (CheckBox)findViewById(R.id.id_observer_activity_cb_stock);
        cb_nba = (CheckBox)findViewById(R.id.id_observer_activity_cb_nba);
        cb_tv = (CheckBox)findViewById(R.id.id_observer_activity_cb_tv);

        btn_boss = (Button)findViewById(R.id.id_observer_activity_btn_boss);
        btn_secretary = (Button)findViewById(R.id.id_observer_activity_btn_secretary);

    }

    private void initEvent(){
        btn_boss.setOnClickListener(this);
        btn_secretary.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.id_observer_activity_btn_boss) {
            boss = new Boss();

            stockObserver = new StockObserver("XM", boss);
            nbaObserver = new NBAObserver("Allever", boss);
            tvObserver = new TVObserver("Winchen", boss);

            if (cb_stock.isChecked()) boss.attach(stockObserver);
            else boss.detach(stockObserver);

            if (cb_nba.isChecked()) boss.attach(nbaObserver);
            else boss.detach(nbaObserver);

            if (cb_tv.isChecked()) boss.attach(tvObserver);
            else boss.detach(tvObserver);


//                boss.attach(stockObserver);
//                boss.attach(nbaObserver);
//                boss.attach(tvObserver);

            boss.setSubjectAction("老板：“ 我回来啦！！！”");
            boss.notification();
        } else if (id == R.id.id_observer_activity_btn_secretary) {
            secretary = new Secretary();

            stockObserver = new StockObserver("XM", secretary);
            nbaObserver = new NBAObserver("Allever", secretary);
            tvObserver = new TVObserver("Winchen", secretary);

//                secretary.attach(stockObserver);
//                secretary.attach(nbaObserver);
//                secretary.attach(tvObserver);


            if (cb_stock.isChecked()) secretary.attach(stockObserver);
            else secretary.detach(stockObserver);

            if (cb_nba.isChecked()) secretary.attach(nbaObserver);
            else secretary.detach(nbaObserver);

            if (cb_tv.isChecked()) secretary.attach(tvObserver);
            else secretary.detach(tvObserver);


            secretary.setSubjectAction("秘书：“老板回来啦！！！”");
            secretary.notification();
        }
    }
}
