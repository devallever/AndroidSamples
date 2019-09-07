package com.allever.mysimple.eventBusTest;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Allever on 2017/1/18.
 */

public class EventBusActivity extends BaseActivity {

    private TextView tv_reaction;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_bus_activity_layout);

        EventBus.getDefault().register(this);

        tv_reaction = (TextView) findViewById(R.id.id_event_bus_activity_tv_reaction);
        setToolbar((Toolbar)findViewById(R.id.id_toolbar),"EventBus");
        ((Button)findViewById(R.id.id_event_bus_activity_btn_startActivity)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventBusActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(UserEvent userEvent){
        tv_reaction.setText(userEvent.getMessage());
    }
}
