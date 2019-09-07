package com.allever.mysimple.nearbyProject.ui;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.nearbyProject.adapter.HeaderBottomAdapter;
import com.allever.mysimple.nearbyProject.listener.LoadDataScrollController;
import com.allever.mysimple.nearbyProject.listener.RecyclerItemClickListener;
import com.allever.mysimple.nearbyProject.pojo.NearbyNewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allever on 2016/11/21.
 */

public class NearbyNewsActivity extends BaseActivity implements LoadDataScrollController.OnRecycleRefreshListener {

    private static final String TAG = "NearbyNewsActivity";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private HeaderBottomAdapter nearbyNewsBaseAdapter;
    //private NearbyNewsBaseAdapterWithHeader nearbyNewsBaseAdapter;
    private List<NearbyNewsItem> list_nearby_news_items = new ArrayList<>();

    private Handler handler;
    private LinearLayoutManager linearLayoutManager;

    private LoadDataScrollController mController;
    private ProgressDialog pd;

    private Toolbar toolbar;
    private ObjectAnimator animator;

    private float mFirstY;
    private float mCurrentY;

    private float mTouchSlop;
    private float direction;
    private boolean mShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_news_activity_layout);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        swipeRefreshLayout.setRefreshing(false);
                        mController.setLoadDataStatus(false);
                        nearbyNewsBaseAdapter.notifyDataSetChanged();
                        recyclerView.setClickable(true);
                        break;
                    case 1:
                        nearbyNewsBaseAdapter.notifyDataSetChanged();
                        mController.setLoadDataStatus(false);
                        //pd.dismiss();
                        dismissProgressDialog();
                        break;
                }
            }
        };



        loadData();
        initView();
        setToolbar(toolbar,"???????");


    }

    private void initView(){

        mTouchSlop = ViewConfiguration.get(NearbyNewsActivity.this).getScaledTouchSlop();

        toolbar = (Toolbar)findViewById(R.id.id_toolbar);
        //animator = new ObjectAnimator();
        mController = new LoadDataScrollController(this);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.id_nearby_news_activity_swipe_refresh_layout);
        recyclerView = (RecyclerView)findViewById(R.id.id_nearby_news_activity_recycler_view);

        //swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorGreen700, R.color.colorGreen700,
                R.color.colorGreen700, R.color.colorGreen700);

        recyclerView = (RecyclerView)findViewById(R.id.id_nearby_news_activity_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        nearbyNewsBaseAdapter = new HeaderBottomAdapter(this,list_nearby_news_items);
        //nearbyNewsBaseAdapter.setHeader(LayoutInflater.from(this).inflate(R.layout.nearby_news_recyclerview_header,recyclerView,false));
        recyclerView.setAdapter(nearbyNewsBaseAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new com.allever.mysimple.nearbyProject.listener.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                show("????");
            }
            @Override
            public void onItemLongClick(View view, int position) {
                show("????");
            }
        }));

        recyclerView.addOnScrollListener(mController);
        swipeRefreshLayout.setOnRefreshListener(mController);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        if (mCurrentY - mFirstY > mTouchSlop){
                            direction = 0;//down
                        }else{
                            direction = 1;//up
                        }
                        if (direction == 1){
                            if (mShow){
                                toolbarAnim(1);//show
                                mShow = !mShow;
                            }
                        }else if (direction == 0){
                            if (!mShow){
                                toolbarAnim(0);//hide
                                mShow = !mShow;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    private void toolbarAnim(int flag){
        if (animator != null && animator.isRunning()){
            animator.cancel();
        }
        if (flag == 0){
            animator = ObjectAnimator.ofFloat(toolbar, "translationY", toolbar.getTranslationY(),0);
        }else{
            animator = ObjectAnimator.ofFloat(toolbar, "translationY", toolbar.getTranslationY(), -toolbar.getHeight());
        }
        animator.start();
    }

    private void loadData(){
        NearbyNewsItem item = new NearbyNewsItem();
        item.setContent("??????????��??????????????????????????");
        item.setNickname("Allever");
        item.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/xm.jpg");
        item.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/3_1.jpg");
        item.setDrawable_head(getResources().getDrawable(R.mipmap.h_01));
        item.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_10));

        NearbyNewsItem item1 = new NearbyNewsItem();
        item1.setContent("?��??????��??????");
        item1.setNickname("Light and Heart");
        item1.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/baobao.jpg");
        item1.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/4_2.jpg");
        item1.setDrawable_head(getResources().getDrawable(R.mipmap.h_02));
        item1.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_09));

        NearbyNewsItem item2 = new NearbyNewsItem();
        item2.setContent("????????????�]");
        item2.setNickname("????");
        item2.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/1.jpg");
        item2.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/46_1.jpg");
        item2.setDrawable_head(getResources().getDrawable(R.mipmap.h_03));
        item2.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_08));

        NearbyNewsItem item3 = new NearbyNewsItem();
        item3.setContent("????????????????");
        item3.setNickname("Girl");
        item3.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/597664927.jpg");
        item3.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/5_2.jpg");
        item3.setDrawable_head(getResources().getDrawable(R.mipmap.h_04));
        item3.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_07));

        NearbyNewsItem item4 = new NearbyNewsItem();
        item4.setContent("?????????????????");
        item4.setNickname("????");
        item4.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/xm.jpg");
        item4.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/3_1.jpg");
        item4.setDrawable_head(getResources().getDrawable(R.mipmap.h_05));
        item4.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_06));

        NearbyNewsItem item5 = new NearbyNewsItem();
        item5.setContent("?????????????????");
        item5.setNickname("???????");
        item5.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/xm.jpg");
        item5.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/3_1.jpg");
        item5.setDrawable_head(getResources().getDrawable(R.mipmap.h_06));
        item5.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_05));

        NearbyNewsItem item6 = new NearbyNewsItem();
        item6.setContent("??????????????????");
        item6.setNickname("????????");
        item6.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/xm.jpg");
        item6.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/3_1.jpg");
        item6.setDrawable_head(getResources().getDrawable(R.mipmap.h_07));
        item6.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_04));

        NearbyNewsItem item7 = new NearbyNewsItem();
        item7.setContent("?????????????????????");
        item7.setNickname("????????");
        item7.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/xm.jpg");
        item7.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/3_1.jpg");
        item7.setDrawable_head(getResources().getDrawable(R.mipmap.h_08));
        item7.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_03));

        NearbyNewsItem item8 = new NearbyNewsItem();
        item8.setContent("???????�E??��?????");
        item8.setNickname("????");
        item8.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/xm.jpg");
        item8.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/3_1.jpg");
        item8.setDrawable_head(getResources().getDrawable(R.mipmap.h_09));
        item8.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_02));

        NearbyNewsItem item9 = new NearbyNewsItem();
        item9.setContent("????????????????????????????????????");
        item9.setNickname("???��????????");
        item9.setHead_path("http://27.54.249.252:8080/SocialServer/images/head/xm.jpg");
        item9.setNews_img("http://27.54.249.252:8080/SocialServer/images/newsimg/3_1.jpg");
        item9.setDrawable_head(getResources().getDrawable(R.mipmap.h_10));
        item9.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_01));




        list_nearby_news_items.add(item);
        list_nearby_news_items.add(item1);
        list_nearby_news_items.add(item2);
        list_nearby_news_items.add(item3);
        list_nearby_news_items.add(item4);
        list_nearby_news_items.add(item5);
        list_nearby_news_items.add(item6);
        list_nearby_news_items.add(item7);
        list_nearby_news_items.add(item8);
        list_nearby_news_items.add(item9);
    }

    @Override
    public void loadMore() {
        //????????????
//        pd = new ProgressDialog(this);
//        //pd.setTitle("???????...");
//        pd.setMessage("???????...");
//        pd.show();
        showProgressDialog("???????...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loadData();
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void refresh() {
        //recyclerView.setClickable(false);
        //show("?????");
        //???????
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    list_nearby_news_items.clear();
                    loadData();
                    handler.sendEmptyMessage(0);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void refreshData(){
        list_nearby_news_items.clear();

        NearbyNewsItem item = new NearbyNewsItem();
        item.setContent("??????????��??????????????????????????");
        item.setNickname("XM");
        item.setHead_path("http://192.168.23.1:8080/SocialServer/images/head/xm.jpg");
        item.setNews_img("http://192.168.23.1:8080/SocialServer/images/newsimg/3_1.jpg");


        NearbyNewsItem item1 = new NearbyNewsItem();
        item1.setContent("??????????��??????????????????????????");
        item1.setNickname("XM");
        item1.setHead_path("http://192.168.23.1:8080/SocialServer/images/head/baobao.jpg");
        item1.setNews_img("http://192.168.23.1:8080/SocialServer/images/newsimg/4_2.jpg");
        item1.setDrawable_head(getResources().getDrawable(R.mipmap.h_02));
        item1.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_06));

        NearbyNewsItem item2 = new NearbyNewsItem();
        item2.setContent("??????????��??????????????????????????");
        item2.setNickname("XM");
        item2.setHead_path("http://192.168.23.1:8080/SocialServer/images/head/1.jpg");
        item2.setNews_img("http://192.168.23.1:8080/SocialServer/images/newsimg/46_1.jpg");
        item2.setDrawable_head(getResources().getDrawable(R.mipmap.h_03));
        item2.setDrawable_news_img(getResources().getDrawable(R.mipmap.h_07));

        NearbyNewsItem item3 = new NearbyNewsItem();
        item3.setContent("??????????��??????????????????????????");
        item3.setNickname("XM");
        item3.setHead_path("http://192.168.23.1:8080/SocialServer/images/head/597664927.jpg");
        item3.setNews_img("http://192.168.23.1:8080/SocialServer/images/newsimg/5_2.jpg");

        list_nearby_news_items.add(item);
        list_nearby_news_items.add(item1);
        list_nearby_news_items.add(item2);
        list_nearby_news_items.add(item3);
    }


}
