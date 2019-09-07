package com.allever.allsample.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

import java.util.List;

/**
 * Created by allever on 17-11-5.
 */

public class BookManagerActivity extends BaseActivity {
    private static final String TAG = "BookManagerActivity";
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);
            mRemoteBookManager = bookManager;
            try {
                List<Book> bookList = bookManager.getBookList();
                Log.d(TAG, "onServiceConnected: query book list list type = " + bookList.getClass().getCanonicalName());
                bookManager.addBook(new Book(3,"艺术探索"));
                bookList = bookManager.getBookList();
                for (Book book:bookList){
                    Log.d(TAG, "onServiceConnected: id = " + book.getBookId());
                    Log.d(TAG, "onServiceConnected: name = " + book.getBookName());
                }
                Log.d(TAG, "onServiceConnected: query book list :" + bookList.toString());
                bookManager.registListener(mOnNewBookArrivedListener);
            }catch (RemoteException re){
                re.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
            mRemoteBookManager = null;
        }
    };

    private IBookManager mRemoteBookManager;
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "handleMessage: receive new book:" + ((Book)msg.obj).getBookName());
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,book).sendToTarget();
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_manager_activity);

        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null &&
                mRemoteBookManager.asBinder().isBinderAlive()){
            try{
                Log.d(TAG, "onDestroy: unregister listener: " + mOnNewBookArrivedListener);
                mRemoteBookManager.unregistListener(mOnNewBookArrivedListener);
            }catch (RemoteException re){
                re.printStackTrace();
            }
        }
        unbindService(connection);
        super.onDestroy();
    }

    public void startService(View view){
//        if (mRemoteBookManager != null){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        mRemoteBookManager.getBookList();
//                    }catch (RemoteException re){
//                        re.printStackTrace();
//                    }
//                }
//            }).start();
//        }
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context, BookManagerActivity.class);
        context.startActivity(intent);
    }
}
