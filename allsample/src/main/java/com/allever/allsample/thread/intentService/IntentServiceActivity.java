package com.allever.allsample.thread.intentService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-7-2.
 */

public class IntentServiceActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_service_activity_layout);

        Intent intentService = new Intent(this,MyIntentService.class);
        startService(intentService);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, IntentServiceActivity.class);
        context.startActivity(intent);
    }
}
