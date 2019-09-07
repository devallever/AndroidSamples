package com.allever.allsample.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by allever on 17-11-4.
 */

public class AdditionService extends Service {

    private static final String TAG = "AdditionService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IAdditionService.Stub(){
            @Override
            public int add(int var1, int var2) throws RemoteException {
                return var1 + var2;
            }

        };
    }
}
