package com.allever.android.sample.aidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.allever.android.sample.Game;
import com.allever.android.sample.IGameManager;
import com.allever.android.sample.R;
import com.allever.lib.common.app.BaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * @author allever
 */
public class AIDLActivity extends BaseActivity {
    private IGameManager mGameManager;
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mGameManager = IGameManager.Stub.asInterface(service);
            try {
                if (mGameManager != null) {
                    for (Game game: mGameManager.getGameList()) {
                        Log.d("Main", game.gameDescribe);
                    }

                    mGameManager.addGame(new Game("数码大冒险","amazing game"));

                    for (Game game: mGameManager.getGameList()) {
                        Log.d("Main", game.gameDescribe);
                    }

                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Intent intent = new Intent(this, AIDLService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }
}
