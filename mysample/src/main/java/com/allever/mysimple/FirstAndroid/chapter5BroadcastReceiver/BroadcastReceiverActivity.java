package com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

/**
 * Created by allever on 17-3-15.
 */

public class BroadcastReceiverActivity extends FirstAndroidBaseActivity{

    private IntentFilter intentFilter;
    private NetWorkChangeReceiver netWorkChangeReceiver;

    private IntentFilter localIntentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_receiver_activity_layout);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //intentFilter.addAction("com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver.LOCAL_BROADCAST");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        registerReceiver(netWorkChangeReceiver,intentFilter);

        localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver.LOCAL_BROADCAST");
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,localIntentFilter);

        Button btn_standard = (Button)findViewById(R.id.id_broadcast_receiver_activity_btn_standard_broadcast);
        btn_standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent standard_broadcast  = new Intent("com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver.MY_BROADCAST");
                sendBroadcast(standard_broadcast);
            }
        });

        Button btn_local = (Button)findViewById(R.id.id_broadcast_receiver_activity_btn_local_broadcast);
        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localBroadcast = new Intent("com.allever.mysimple.FirstAndroid.chapter5BroadcastReceiver.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(localBroadcast);
            }
        });

        Button btn_bestPractice = (Button)findViewById(R.id.id_broadcast_receiver_activity_btn_broadcast_best_practice);
        btn_bestPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BroadcastReceiverActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    class NetWorkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context,"Network Change !!!!",Toast.LENGTH_LONG).show();

            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(context,"Network available !!!!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context,"Network unavailable !!!!",Toast.LENGTH_LONG).show();
            }

        }
    }

    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Received local broadcast !!!!",Toast.LENGTH_LONG).show();
        }
    }
}
