package com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by allever on 17-3-15.
 */

public class LoginSuccessActivity extends FirstAndroidBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success_activity_layout);

        Button btn_send = (Button)findViewById(R.id.id_login_success_activity_btn_send_broadcast);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcastIntent = new Intent("com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver.FORCE_OFFLINE");
                sendBroadcast(broadcastIntent);
            }
        });
    }
}
