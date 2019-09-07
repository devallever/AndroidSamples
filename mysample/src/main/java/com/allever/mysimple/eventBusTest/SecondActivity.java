package com.allever.mysimple.eventBusTest;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
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

public class SecondActivity extends BaseActivity {

    private TextView tv_reaction;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_layout);

        EventBus.getDefault().register(this);

        tv_reaction = (TextView) findViewById(R.id.id_second_activity_tv_reaction);

        ((Button)findViewById(R.id.id_second_activity_btn_start_activity)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(UserEvent userEvent){
        tv_reaction.setText(userEvent.getMessage());
    }
}
