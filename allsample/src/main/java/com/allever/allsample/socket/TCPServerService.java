package com.allever.allsample.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by allever on 17-11-9.
 */

public class TCPServerService  extends Service {
    private static final String TAG = "TCPServerService";
    private boolean mIsServiceDestroyed = false;
    private String[] mReplyMessage = new String[]{
            "你好呀，哈哈",
            "请问你叫什么名字",
            "今天北京天气很不错",
            "你知道吗，我可是可以和很多个人同时聊天",
            "跟你讲个笑话吧"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new TcpService().start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TcpService extends Thread{
        @Override
        public void run() {
            Log.d(TAG, "run: ");
            ServerSocket serverSocket = null;
            try {
                //监听本地8688
                serverSocket = new ServerSocket(8688);
            }catch (IOException ioe){
                Log.d(TAG, "run: connect fail");
                ioe.printStackTrace();
                return;
            }

            while (!mIsServiceDestroyed){
                try {
                    //接受客户端请求
                    final Socket clientSocket = serverSocket.accept();
                    Log.d(TAG, "run: accept a client");
                    new ClientThread(clientSocket).start();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }

            }
        }
    }

    private class ClientThread extends Thread{
        private Socket mClientSocket = null;
        public ClientThread(Socket clientScoket){
            this.mClientSocket = clientScoket;
        }
        @Override
        public void run() {
            try {
                responseClient(mClientSocket);
            }catch (IOException ioe){
                ioe.printStackTrace();
            }

        }
    }

    private void responseClient(Socket clientSocket) throws IOException{
        //读取客户端发来的消息
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //用于想客户端发送消息
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        Log.d(TAG, "responseClient: 欢迎来到聊天室");
        while (!mIsServiceDestroyed){
            String inStr = bufferedReader.readLine();
            Log.d(TAG, "responseClient: clientSocket: " + inStr);
            if (inStr == null){
                //断开连接
                break;
            }
            int index = new Random().nextInt(mReplyMessage.length);
            String outStr = mReplyMessage[index];
            Log.d(TAG, "responseClient: server: " + outStr);
            printWriter.println(outStr);
            Log.d(TAG, "responseClient: send: " + outStr);
        }

        Log.d(TAG, "responseClient: client quit");
        printWriter.close();
        bufferedReader.close();
        clientSocket.close();


    }
}
