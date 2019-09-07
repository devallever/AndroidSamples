package com.allever.mysimple.eventBusTest;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Allever on 2017/1/18.
 */

public class ThirdActivity extends BaseActivity {
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity_layout);

        ((Button)findViewById(R.id.id_third_activity_btn_start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ThirdActivity.this,FourActivity.class);
//                startActivity(intent);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new UserEvent("I'm xm."));
                    }
                }).start();
            }
        });
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onUserEvent(UserEvent userEvent){
//        ((TextView)findViewById(R.id.id_third_activity_tv_reaction)).setText(userEvent.getMessage());
//    }
}
