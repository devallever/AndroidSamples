package com.allever.allsample.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by allever on 17-11-5.
 * 远程服务端Service
 */

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    private CopyOnWriteArrayList<Book> mBookLis = new CopyOnWriteArrayList<>();
    //private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();
    private AtomicBoolean mIsServiceDestroy = new AtomicBoolean(false);
    
    private Binder mBinder = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(5000);
            return mBookLis;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookLis.add(book);
        }

        @Override
        public void registListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
//            if (!mListenerList.contains(listener)){
//                mListenerList.add(listener);
//            }else {
//                Log.d(TAG, "registListener: listener exist");
//            }
//            Log.d(TAG, "registListener: listenerList.size = " + mListenerList.size());
        }

        @Override
        public void unregistListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
//            if (mListenerList.contains(listener)){
//                mListenerList.remove(listener);
//                Log.d(TAG, "unregistListener: unregister listener succeed");
//            }else {
//                Log.d(TAG, "unregistListener: not found the listener, can not unregister");
//            }
//            Log.d(TAG, "unregistListener: current listenerList.size = " + mListenerList.size());

        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookLis.add(new Book(1,"Android"));
        mBookLis.add(new Book(2,"Java"));
        new Thread(new ServiceWorker()).start();
    }


    private void onNewBookArrived(Book book)throws RemoteException{
        mBookLis.add(book);
        final int N = mListenerList.beginBroadcast();
        for (int i=0; i<N; i++){
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null){
                try {
                    listener.onNewBookArrived(book);
                }catch (RemoteException re){
                    re.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
//        mBookLis.add(book);
//        Log.d(TAG, "onNewBookArrived: notify listeners:" + mListenerList.size());
//        for (IOnNewBookArrivedListener listener:mListenerList){
//            Log.d(TAG, "onNewBookArrived: notify listener: " + listener);
//            listener.onNewBookArrived(book);
//        }
    }
    private class ServiceWorker implements Runnable{
        @Override
        public void run() {
            while (!mIsServiceDestroy.get()){
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                int bookId = mBookLis.size() + 1;
                Book book = new Book(bookId,"new Book#" + bookId);
                try {
                    onNewBookArrived(book);
                }catch (RemoteException re){
                    re.printStackTrace();
                }
            }
        }
    }
}
