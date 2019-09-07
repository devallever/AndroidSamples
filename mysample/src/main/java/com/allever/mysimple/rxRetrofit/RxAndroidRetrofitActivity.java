package com.allever.mysimple.rxRetrofit;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allever.mysimple.BaseActivity;
import com.allever.mysimple.R;
import com.allever.mysimple.rxRetrofit.bean.User;
import com.allever.mysimple.rxRetrofit.retrofit.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Allever on 2017/1/15.
 */

public class RxAndroidRetrofitActivity extends BaseActivity{

    private static final String TAG = "RxRetrofitActivity";

//    @BindView(R.id.id_rx_retrofit_btn_base)
//    Button btn_base;
//
//    @BindView(R.id.id_rx_retrofit_btn_schedule)
//    Button btn_schedule;
//
//    @BindView(R.id.id_rx_retrofit_btn_flatmap)
//    Button btn_flatmap;
//
//    @BindView(R.id.id_rx_retrofit_btn_map)
//    Button btn_map;
//
//    @BindView(R.id.id_rx_retrofit_btn_action)
//    Button btn_action;
//
//    @BindView(R.id.id_rx_retrofit_btn_just)
//    Button btn_just;
//
//    @BindView(R.id.id_rx_retrofit_btn_from)
//    Button btn_print_from;
//
//    @BindView(R.id.id_rx_retrofit_btn_search)
//    Button btn_search;
//    @BindView(R.id.id_rx_retrofit_tv_result)
//    TextView tv_result;


    private TextView tv_result;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_android_retorfit_activity_layout);

        setToolbar((Toolbar) findViewById(R.id.id_toolbar),"RxJava+Retrofit");

        tv_result = (TextView)findViewById(R.id.id_rx_retrofit_tv_result);

        //ButterKnife.bind(this);

        Button btn_base = (Button)findViewById(R.id.id_rx_retrofit_btn_base);
        btn_base.setOnClickListener(v->{
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    subscriber.onNext("Hello");
                    subscriber.onNext("world");
                    subscriber.onCompleted();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: ");
                        }

                        @Override
                        public void onNext(String s) {
                            Toast.makeText(RxAndroidRetrofitActivity.this,s,Toast.LENGTH_LONG).show();
                        }
                    });
        });

        Button btn_search = (Button)findViewById(R.id.id_rx_retrofit_btn_search);
        btn_search.setOnClickListener(v ->
            RetrofitUtil.getInstance().getSignature(new Subscriber<User>() {
                @Override
                public void onCompleted() {
                }
                @Override
                public void onError(Throwable e) {
                    Toast.makeText(RxAndroidRetrofitActivity.this,"查找失败",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNext(User user) {
                    tv_result.setText("签名："+ user.getUser().getSignature());
                }
            },"xm")
        );


        Button btn_print_from = (Button)findViewById(R.id.id_rx_retrofit_btn_from);
        btn_print_from.setOnClickListener(v -> {
            //Toast.makeText(RxAndroidRetrofitActivity.this, "from", Toast.LENGTH_LONG).show();
            String[] array = {"xm","allever", "winchen"};
            Observable.from(array)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            Toast.makeText(RxAndroidRetrofitActivity.this,s,Toast.LENGTH_LONG).show();
                        }
                    });
        });

        Button btn_just = (Button)findViewById(R.id.id_rx_retrofit_btn_just);
        btn_just.setOnClickListener(v ->{
            Observable.just("xm","allever", "baobao")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            Toast.makeText(RxAndroidRetrofitActivity.this,s,Toast.LENGTH_LONG).show();
                        }
                    });
        });

        Button btn_action = (Button)findViewById(R.id.id_rx_retrofit_btn_action);
        btn_action.setOnClickListener(v->{
            Action1<String> nextAction = new Action1<String>() {
                @Override
                public void call(String s) {
                    Log.d(TAG, "call: " + s);
                    Toast.makeText(RxAndroidRetrofitActivity.this,s,Toast.LENGTH_LONG).show();
                }
            };
            Action1<Throwable> errorAction = new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.d(TAG, "call: errorAction");
                }
            };

            Action0 completedAction = new Action0() {
                @Override
                public void call() {
                    Log.d(TAG, "call: completeAction");
                }
            };
            
            String[] array = {"xm", "allever","winchen", "mansore"};
            Observable.from(array)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(nextAction,errorAction,completedAction);
        });

        Button btn_map = (Button)findViewById(R.id.id_rx_retrofit_btn_map);
        btn_map.setOnClickListener(v->{
            List<Student> students = new ArrayList<Student>();
            Course courseJava = new Course("Java",1);
            Course courseAndroid = new Course("Android",2);
            Course javaWeb = new Course("Java Web",3);
            Course coursePs = new Course("PhotoShop",4);

            List<Course> cursors1 = new ArrayList<Course>();
            cursors1.add(javaWeb);
            cursors1.add(courseJava);
            students.add(new Student("xm",23,cursors1));

            List<Course> cursors2 = new ArrayList<Course>();
            cursors2.add(courseAndroid);
            cursors2.add(coursePs);
            students.add(new Student("winchen",25,cursors2));


            List<Course> cursors3 = new ArrayList<Course>();
            cursors3.add(javaWeb);
            cursors3.add(coursePs);
            students.add(new Student("allever",24,cursors3));

            List<Course> cursors4 = new ArrayList<Course>();
            cursors4.add(javaWeb);
            cursors4.add(courseAndroid);
            students.add(new Student("lios",22,cursors4));

            Observable.from(students)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<Student, String>() {
                        @Override
                        public String call(Student student) {
                            return student.name;
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            Toast.makeText(RxAndroidRetrofitActivity.this,s,Toast.LENGTH_LONG).show();
                        }
                    });
        });


        Button btn_flatmap = (Button)findViewById(R.id.id_rx_retrofit_btn_flatmap);
        btn_flatmap.setOnClickListener(v->{
            List<Student> students = new ArrayList<Student>();
            Course courseJava = new Course("Java",1);
            Course courseAndroid = new Course("Android",2);
            Course javaWeb = new Course("Java Web",3);
            Course coursePs = new Course("PhotoShop",4);

            List<Course> cursors1 = new ArrayList<Course>();
            cursors1.add(javaWeb);
            cursors1.add(courseJava);
            students.add(new Student("xm",23,cursors1));

            List<Course> cursors2 = new ArrayList<Course>();
            cursors2.add(courseAndroid);
            cursors2.add(coursePs);
            students.add(new Student("winchen",25,cursors2));


            List<Course> cursors3 = new ArrayList<Course>();
            cursors3.add(javaWeb);
            cursors3.add(coursePs);
            students.add(new Student("allever",24,cursors3));

            List<Course> cursors4 = new ArrayList<Course>();
            cursors4.add(javaWeb);
            cursors4.add(courseAndroid);
            students.add(new Student("lios",22,cursors4));

            Observable.from(students)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1<Student, Observable<Course>>() {
                        @Override
                        public Observable<Course> call(Student student) {
                            return Observable.from(student.cursros);
                        }
                    })
                    .subscribe(new Action1<Course>() {
                        @Override
                        public void call(Course course) {
                            Log.d(TAG, "call: " + course.cursor_name);
                            Toast.makeText(RxAndroidRetrofitActivity.this,course.cursor_name,Toast.LENGTH_LONG).show();
                        }
                    });
        });


        Button btn_schedule = (Button)findViewById(R.id.id_rx_retrofit_btn_schedule);
        btn_schedule.setOnClickListener(v->{
            List<Student> students = new ArrayList<Student>();
            Course courseJava = new Course("Java",1);
            Course courseAndroid = new Course("Android",2);
            Course javaWeb = new Course("Java Web",3);
            Course coursePs = new Course("PhotoShop",4);

            List<Course> cursors1 = new ArrayList<Course>();
            cursors1.add(javaWeb);
            cursors1.add(courseJava);
            students.add(new Student("xm",23,cursors1));

            List<Course> cursors2 = new ArrayList<Course>();
            cursors2.add(courseAndroid);
            cursors2.add(coursePs);
            students.add(new Student("winchen",25,cursors2));


            List<Course> cursors3 = new ArrayList<Course>();
            cursors3.add(javaWeb);
            cursors3.add(coursePs);
            students.add(new Student("allever",24,cursors3));

            List<Course> cursors4 = new ArrayList<Course>();
            cursors4.add(javaWeb);
            cursors4.add(courseAndroid);
            students.add(new Student("lios",22,cursors4));

            Observable.from(students)
                    .subscribeOn(Schedulers.io())               //io线程发起
                    .observeOn(Schedulers.io())                 //io线程处理
                    .map(new Func1<Student, String>() {
                        @Override
                        public String call(Student student) {
                            Log.d(TAG, "call: IO线程不应该Toast");
                            //Toast.makeText(RxAndroidRetrofitActivity.this,"io线程不应该Toast" +  student.name,Toast.LENGTH_LONG).show();报错
                            return student.name;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())  //最后由主线程显示
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            Toast.makeText(RxAndroidRetrofitActivity.this, s, Toast.LENGTH_LONG).show();
                        }
                    });
        });

    }


    private class Student{
        private String name;
        private int age;
        private List<Course> cursros;
        public Student(String name, int age, List<Course> cursor){
            this.cursros = cursor;
            this.name = name;
            this.age = age;
        }
    }

    private class Course{
        private String cursor_name;
        private int id;
        public Course(String cursor_name,int id){
            this.cursor_name =cursor_name;
            this.id = id;
        }
    }
}
