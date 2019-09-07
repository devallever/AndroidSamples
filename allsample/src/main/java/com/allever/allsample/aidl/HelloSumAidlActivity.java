package com.allever.allsample.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-11-4.
 */

public class HelloSumAidlActivity extends BaseActivity {

    private static final String TAG = "HelloSumAidlActivity";

    private IAdditionService iAdditionService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            iAdditionService = IAdditionService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sum_add_activity);

        initService();

        Button buttonCalc = (Button)findViewById(R.id.buttonCalc);
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            EditText value1 = (EditText)findViewById(R.id.value1);
            EditText value2= (EditText)findViewById(R.id.value2);
            TextView result = (TextView)findViewById(R.id.result);
            @Override
            public void onClick(View v) {
                int v1, v2, res = -1;
                v1 = Integer.parseInt(value1.getText().toString());
                v2 = Integer.parseInt(value2.getText().toString());

                try {
                    res = iAdditionService.add(v1, v2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                result.setText(Integer.valueOf(res).toString());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }

    private void initService() {
        Intent i = new Intent(this,AdditionService.class);
        //i.setClassName("com.allever.allsample.aidl", com.allever.allsample.aidl.AdditionService.class.getName());
        startService(i);
        bindService(i, connection, Context.BIND_AUTO_CREATE);
    }

    private void releaseService() {
        unbindService(connection);
        connection = null;
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context,HelloSumAidlActivity.class);
        context.startActivity(intent);
    }

}
