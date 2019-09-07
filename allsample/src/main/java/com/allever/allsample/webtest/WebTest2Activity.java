package com.allever.allsample.webtest;

/**
 * Created by allever on 17-8-16.
 */

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;

public class WebTest2Activity extends BaseActivity {

    private WebView webView;
    private LinearLayout ll_root;
    private EditText et_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_test_2);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        et_user = (EditText) findViewById(R.id.et_user);
        initWebView();
    }

    //初始化WebView

    private void initWebView() {
        //动态创建一个WebView对象并添加到LinearLayout中
        //webView = new WebView(getApplication());
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //webView.setLayoutParams(params);
        //ll_root.addView(webView);


        webView = (WebView)findViewById(R.id.id_web_test_2_wb);
        //不跳转到其他浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //加载本地html文件
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        //加载网页
        webView.loadUrl("https://www.baidu.com/");
        webView.addJavascriptInterface(new JSInterface(),"Android");
    }

    //按钮的点击事件
    public void click(View view){
        //java调用JS方法
        webView.loadUrl("javascript:javaCallJs(" + "'" + et_user.getText().toString()+"'"+")");
        webView.loadUrl("javascript:showAlert()");
    }

    //在页面销毁的时候将webView移除
/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        ll_root.removeView(webView);
        webView.stopLoading();
        webView.removeAllViews();
        webView.destroy();
        webView = null;
    }*/

    private class JSInterface {
        //JS需要调用的方法
        @JavascriptInterface
        public void showToast(String arg){
            Toast.makeText(WebTest2Activity.this,arg,Toast.LENGTH_SHORT).show();
        }
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context, WebTest2Activity.class);
        context.startActivity(intent);
    }

}