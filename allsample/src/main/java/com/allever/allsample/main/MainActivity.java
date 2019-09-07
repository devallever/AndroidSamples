package com.allever.allsample.main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.allever.allsample.BaseActivity;
import com.allever.allsample.R;
import com.allever.allsample.aidl.BookManagerActivity;
import com.allever.allsample.aidl.HelloSumAidlActivity;
import com.allever.allsample.bitmapoom.BitmapTestActivity;
import com.allever.allsample.customView.CustomViewActivity;
import com.allever.allsample.dialog.DialogActivity;
import com.allever.allsample.main.adapter.MainRecyclerBaseAdapter;
import com.allever.allsample.main.bean.ListItem;
import com.allever.allsample.mvc.WeatherActivity;
import com.allever.allsample.mvp2.MVP2Activity;
import com.allever.allsample.okhttp.OkHttpMainActivity;
import com.allever.allsample.scroll.ScrollTestActivity;
import com.allever.allsample.scroll.ViewDragHelperTestActivity;
import com.allever.allsample.serialize.SerializeTestActivity;
import com.allever.allsample.socket.TCPClientActivity;
import com.allever.allsample.sqlite.SqliteMainActivity;
import com.allever.allsample.thread.ThreadActivity;
import com.allever.allsample.util.FileUtil;
import com.allever.allsample.webtest.WebTest2Activity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainRecyclerBaseAdapter.OnItemClickListener{
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private MainRecyclerBaseAdapter mainRecyclerBaseAdapter;
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();

        String url = "https://github.com/devallever/MyCoolWeather/blob/master/app/simpleWeather.apk";
        FileUtil.getExternalDownloadDir();
        Log.d(TAG, "onCreate: name = " + FileUtil.getNameFromUrl(url));

        //FileUtil.createFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/allSample/test/hello.apk");
        createFile();

        createJson();

    }
    private void createJson(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("key_1",1);
            jsonObj.put("key_2","value");
            jsonObj.put("key_3",false);
            jsonObj.put("key_4",0.3f);
            jsonObj.put("key_5",3L);
            jsonObj.put("key_6",new Object());
            jsonObj.put("key_7",new ArrayList<>());
            Log.d(TAG, "createJson:  = \n" + jsonObj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createFile(){
    }

    private void initData(){
        listItems = new ArrayList<>();
        listItems.add(new ListItem("OkHttp","OkHttp"));
        listItems.add(new ListItem("Thread","Thread, HandlerThread, AsyncTask, IntentService"));
        listItems.add(new ListItem("Custom View","FreshTextView"));
        listItems.add(new ListItem("Scroll Methods","Seven Type:layout()方法, offset, layoutParams, scrollToScrollBy,scroller,animation,viewDragHelper"));
        listItems.add(new ListItem("ViewDragHelper","ViewDragHelperTest"));
        listItems.add(new ListItem("WebViewTest","web view test"));
        listItems.add(new ListItem("DialogTest","all kinds of dialog"));
        listItems.add(new ListItem("MVC","MVC Weather"));
        listItems.add(new ListItem("Serialize","序列化"));
        listItems.add(new ListItem("aidl","aidl demo"));
        listItems.add(new ListItem("aidl","aidl demo bookManager"));
        listItems.add(new ListItem("bitmap-oom","oom demo"));
        listItems.add(new ListItem("socket","socket 聊天室"));
        listItems.add(new ListItem("mvp2","mvp demo"));
        listItems.add(new ListItem("SQLite", "sqlite demo"));
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this,listItems.get(position).getTitle(),Toast.LENGTH_LONG).show();
        switch (position){
            case 0:
                OkHttpMainActivity.startActivity(this);
                break;
            case 1:
                ThreadActivity.startActivity(this);
                break;
            case 2:
                CustomViewActivity.startActivity(this);
                break;
            case 3:
                ScrollTestActivity.startActivity(this);
                break;
            case 4:
                ViewDragHelperTestActivity.startActivity(this);
                break;
            case 5:
                WebTest2Activity.startAction(this);
                break;
            case 6:
                DialogActivity.startAction(this);
                break;
            case 7:
                WeatherActivity.startActivity(this);
                break;
            case 8:
                SerializeTestActivity.startAction(this);
                break;
            case 9:
                HelloSumAidlActivity.startAction(this);
                break;
            case 10:
                BookManagerActivity.startAction(this);
                break;
            case 11:
                BitmapTestActivity.startAction(this);
                break;
            case 12:
                TCPClientActivity.startAction(this);
                break;
            case 13:
                MVP2Activity.startAction(this);
                break;
            case 14:
                SqliteMainActivity.startAction(this);
                break;
            default:
                break;
        }
    }

    private void initView(){
        recyclerView = (RecyclerView)findViewById(R.id.id_main_activity_recycler_view);
        mainRecyclerBaseAdapter = new MainRecyclerBaseAdapter(this,listItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerBaseAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mainRecyclerBaseAdapter);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
    }
}
