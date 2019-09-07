package com.allever.allsample.webtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

/**
 * Created by allever on 17-8-16.
 */

public class WebTestActivity extends BaseActivity{
    private static final String TAG = "WebTestActivity";
    private String mUsername;
    private WebView webView;
    private Button btn_call_js;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_test_activity_layout);
        mUsername = getIntent().getStringExtra("username");
        Log.d(TAG, "onCreate: username = " + mUsername);
        initView();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initView(){
        btn_call_js = (Button)findViewById(R.id.id_web_test_activity_btn_call_js);
        btn_call_js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //webView.loadUrl("javascript:javaCallJs('" + mUsername + "')");
                webView.loadUrl("javascript:javaCallJs()");
            }
        });
        webView = (WebView)findViewById(R.id.id_web_test_activity_web_view);
        WebSettings settings = webView.getSettings();
        //设置支持javaScript脚步语言
        settings.setJavaScriptEnabled(true);
        //支持双击-前提是页面要支持才显示
        settings.setUseWideViewPort(true);
        //支持缩放按钮-前提是页面要支持才显示
        settings.setBuiltInZoomControls(true);
        //设置客户端-不跳转到默认浏览器中
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new AndroidAndJsInterface(), "Android");



        //加载网页地址
        webView.loadUrl("file:///android_asset/index.html");
        //Log.d(TAG, "javascript:javaCallJs('" + mUsername + "')");



    }

    public static void startAction(Context context,String username){
        Intent intent = new Intent(context, WebTestActivity.class);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    public class AndroidAndJsInterface {
        /**
         * Js中调用的方法
         */
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(WebTestActivity.this, "我是原生代码!", Toast.LENGTH_SHORT).show();
        }

    }

}
