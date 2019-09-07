package com.allever.mysimple.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.FirstAndroid.FirstAndroidMainActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.activityTransition.ActivityA;
import com.allever.mysimple.animator.AnimatorTestActivity;
import com.allever.mysimple.decoratorPattern.DecoratorActivity;
import com.allever.mysimple.eventBusTest.EventBusActivity;
import com.allever.mysimple.imageloader.activity.ImageLoaderActivity;
import com.allever.mysimple.main.adapter.MyRecyclerViewAdapter;
import com.allever.mysimple.main.bean.ListItem;
import com.allever.mysimple.main.listener.RecyclerItemClickListener;
import com.allever.mysimple.matetialTest.MaterialActivity;
import com.allever.mysimple.mvpTest.MvpMainActivity;
import com.allever.mysimple.nearbyProject.ui.NearbyNewsActivity;
import com.allever.mysimple.newsDetail.NewsDetailActivity;
import com.allever.mysimple.observerPattern.ObserverActivity;
import com.allever.mysimple.retrofit.RetrofitActivity;
import com.allever.mysimple.rxAndroid.RxAndroidActivity;
import com.allever.mysimple.rxRetrofit.RxAndroidRetrofitActivity;
import com.allever.mysimple.simpleFactory.CalculatorActivity;
import com.allever.mysimple.singleton.SingleTonActivity;
import com.allever.mysimple.strategyModel.CashActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<ListItem> listItems;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    private RelativeLayout rl_main_container;

    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();


        //setLayoutAnimation();
    }

    private void setLayoutAnimation(){
        //ScaleAnimation scaleAnimation = new ScaleAnimation(0,1f,0,1f,20f,20f);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1f, 0.0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //Animation.
        scaleAnimation.setDuration(1000);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(scaleAnimation, 0.5f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        rl_main_container.setLayoutAnimation(layoutAnimationController);
    }

    private void initData(){
        listItems = new ArrayList<>();
        listItems.add(new ListItem("Hello World","description"));
        listItems.add(new ListItem("AnimatorTest","Android属性动画完全解析(上)，初识属性动画的基本用法"));
        listItems.add(new ListItem("计算器","使用简单工厂模式实现计算器，运用面向对象三大特征：封装，继承和多态"));
        listItems.add(new ListItem("计费软件", "使用策略模式实现的计费小工具"));
        listItems.add(new ListItem("ImageLoader", "使用自定义的ImageLoader加载图片"));
        listItems.add(new ListItem("选择服装", "使用装饰模式选择服装"));
        listItems.add(new ListItem("打小报告", "使用观察者模式实现通知"));
        listItems.add(new ListItem("附近","附近人，附近动态图\n练习RecyclerView，下拉刷新，上拉加载，动态选择布局,分页加载,添加头部尾部, 使用Retrofit网络库，注解"));
        listItems.add(new ListItem("Activity过度动画", "包括Activity过渡动画，共享元素动画效果"));
        listItems.add(new ListItem("单例模式", " 使用双重锁定和静态内部类方式创建单例"));
        listItems.add(new ListItem("Retrofit","retrofit"));
        listItems.add(new ListItem("RxAndroid","RxJava只是ReactiveX(Reactive Extensions)的一种Java实现, ReactiveX是一种响应式扩展框架"));
        listItems.add(new ListItem("RxJava + Retrofit", "RxJava + Retrofit 的小demo"));
        listItems.add(new ListItem("Material Design", "Material Design 设计规范：Toolbar，FAB,抽屉布局，导航布局等等....."));
        listItems.add(new ListItem("EventBus","事件总线"));
        listItems.add(new ListItem("MVP","使用MVP + Retrofit + RxJava的小demo"));
        listItems.add(new ListItem("FirstAndroid","第一行代码Android的demo"));
        listItems.add(new ListItem("newsDetail","newsDetails"));
    }

    private void initView(){
        recyclerView = (RecyclerView)this.findViewById(R.id.id_main_recycler_view);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this,listItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,listItems.get(position).getTitle(),Toast.LENGTH_LONG).show();
                Intent intent;
                switch (position){
                    case 1:
                        intent = new Intent(MainActivity.this, AnimatorTestActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, CalculatorActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, CashActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, ImageLoaderActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, DecoratorActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, ObserverActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        ///intent = new Intent(MainActivity.this, NearbyActivity.class);
                        intent = new Intent(MainActivity.this,NearbyNewsActivity.class);
                        Log.d("","");
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(MainActivity.this, ActivityA.class);
                        //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        //startActivity(intent);
                        show("Android 5.0 以上");
                        break;
                    case 9:
                        intent = new Intent(MainActivity.this, SingleTonActivity.class);
                        startActivity(intent);
                        break;
                    case 10:
                        intent = new Intent(MainActivity.this, RetrofitActivity.class);
                        startActivity(intent);
                        break;
                    case 11:
                        intent= new Intent(MainActivity.this, RxAndroidActivity.class);
                        startActivity(intent);
                        break;
                    case 12:
                        intent = new Intent(MainActivity.this, RxAndroidRetrofitActivity.class);
                        startActivity(intent);
                        break;
                    case 13:
                        intent = new Intent(MainActivity.this, MaterialActivity.class);
                        startActivity(intent);
                        break;
                    case 14:
                        intent = new Intent(MainActivity.this, EventBusActivity.class);
                        startActivity(intent);
                        break;
                    case 15:
                        intent = new Intent(MainActivity.this, MvpMainActivity.class);
                        startActivity(intent);
                        break;
                    case 16:
                        intent = new Intent(MainActivity.this, FirstAndroidMainActivity.class);
                        startActivity(intent);
                        break;
                    case 17:
                        intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                        startActivity(intent);
                        break;

                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this,"LongClick",Toast.LENGTH_LONG).show();
            }
        }));

        rl_main_container = (RelativeLayout)this.findViewById(R.id.id_activity_main);

    }



}
