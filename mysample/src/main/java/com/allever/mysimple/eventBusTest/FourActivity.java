package com.allever.mysimple.eventBusTest;

import androidx.annotation.LayoutRes;
import android.view.View;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Allever on 2017/1/18.
 */

public class FourActivity extends BaseActivity {

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setContentView(R.layout.four_activity_layout);

        findViewById(R.id.id_four_activity_btn_send_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new UserEvent("I'm xm."));
            }
        });
    }
}
