package com.allever.allsample.socket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by allever on 17-11-9.
 */

public class TCPClientActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "TCPClientActivity";

    private static final int MESSAGE_SOCKET_CONNECTED = 0x01;
    private static final int MESSAGE_SOCKET_RECEIVE_NEW_MSG = 0x02;

    private Button btn_send;
    private TextView tv_display;
    private EditText et_input;

    private Socket mClientSocket;
    private PrintWriter mPrintWriter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_SOCKET_CONNECTED:
                    btn_send.setEnabled(true);
                    break;
                case MESSAGE_SOCKET_RECEIVE_NEW_MSG:
                    tv_display.setText(tv_display.getText() + (String)msg.obj);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcp_client_activity);

        initView();

        //开启服务
        startTcpServer();
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        super.onDestroy();
    }

    private void startTcpServer(){
        Intent intent = new Intent(this,TCPServerService.class);
        startService(intent);
        new ConnectTCPThread().start();
    }
    private void initView(){
        btn_send = (Button)findViewById(R.id.id_tcp_btn_send);
        et_input = (EditText)findViewById(R.id.id_tcp_et_input);
        tv_display = (TextView)findViewById(R.id.id_tcp_tv_display);

        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.id_tcp_btn_send) {
            sendMessageToServer();
        }
    }

    private void sendMessageToServer(){
        final String msg = et_input.getText().toString();
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
            mPrintWriter.println(msg);
            et_input.setText("");
            String time = formatDataTime(System.currentTimeMillis());
            final String showMsg = "self " + time + ": " + msg + "\n";
            tv_display.setText(tv_display.getText().toString() + showMsg);


        }
    }

    private class ConnectTCPThread extends Thread{
        @Override
        public void run() {
            Log.d(TAG, "run: ");
            connectTCPServer();
        }
    }

    private void connectTCPServer(){
        Log.d(TAG, "connectTCPServer: ");
        Socket socket = null;
        while (socket == null){
            try {
                socket = new Socket("10.42.0.94",8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d(TAG, "connectTCPServer: connect tcp server success;");
            }catch (IOException ioe){
                //重连机制
                SystemClock.sleep(1000);
                Log.d(TAG, "connectTCPServer: connect tcp server fail.");
                ioe.printStackTrace();
            }
        }
        try {
            //接受服务端消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.d(TAG, "connectTCPServer: aaaaa");
            while (!TCPClientActivity.this.isFinishing()){
                String inStr = bufferedReader.readLine();
                Log.d(TAG, "connectTCPServer: receive: " + inStr);
                if (inStr != null){
                    String time = formatDataTime(System.currentTimeMillis());
                    final String showMessage = "server " + time + ": " + inStr + "\n";
                    mHandler.obtainMessage(MESSAGE_SOCKET_RECEIVE_NEW_MSG,showMessage).sendToTarget();
                    Log.d(TAG, "connectTCPServer: " + showMessage);
                }
            }
            Log.d(TAG, "connectTCPServer: quit ...");
            mPrintWriter.close();
            bufferedReader.close();
            socket.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private String formatDataTime(long time){
        return new SimpleDateFormat("(hh:mm:ss)").format(new Date(time));
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context, TCPClientActivity.class);
        context.startActivity(intent);
    }
}
