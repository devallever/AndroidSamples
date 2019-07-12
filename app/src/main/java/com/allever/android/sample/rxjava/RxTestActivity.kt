package com.allever.android.sample.rxjava

import android.os.Bundle

import com.allever.android.sample.R
import com.allever.lib.common.app.BaseActivity
import com.allever.lib.common.util.DLog

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.schedulers.Schedulers

class RxTestActivity : BaseActivity() {

    private var mDisposable: Disposable? = null
    private var mDisposableContainer: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_test)

        val threadName = Thread.currentThread().name
        DLog.d("onCreate mainThread: pid = $threadName")

        //rxJava基本用法
        rxJavaStandard()
        rxJavaMap()

    }

    private fun rxJavaMap() {
        Observable.create(ObservableOnSubscribe<Int> {
            //2.
            DLog.d("subscribe: threadName = ${Thread.currentThread().name}")
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onComplete()
        })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            //原始类型可以转Any任何类型
            .map(object : Function<Int, User> {
                override fun apply(it: Int): User {
                    return User(it)
                }
            })
            .subscribe(object : Observer<User> {
                override fun onSubscribe(disposable: Disposable) {
                    DLog.d("onSubscribe: threadName = ${Thread.currentThread().name}")
                    mDisposable = disposable
                    mDisposableContainer?.add(disposable)
                }

                override fun onNext(user: User) {
                    //3.
                    DLog.d("onNext: ${user.id}")
                    DLog.d("onNext: threadName = ${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {
                    DLog.d("onError: threadName = ${Thread.currentThread().name}")
                }

                override fun onComplete() {
                    DLog.d("onComplete: threadName = ${Thread.currentThread().name}")
                }
            })
    }

    private fun rxJavaStandard() {
        //默认订阅发生的线程和观察的线程相同
        //Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
        //Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
        //Schedulers.newThread() 代表一个常规的新线程
        //AndroidSchedulers.mainThread()  代表Android的主线程
        Observable.create(ObservableOnSubscribe<Int> {
            //2.
            DLog.d("subscribe: threadName = ${Thread.currentThread().name}")
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            //onComplete和onError只能存在一个，当调用onComplete或者onError后，onNext不会发送
            it.onComplete()
            //it.onError(Exception())
            it.onNext(4)
            it.onNext(5)
        })
            //新建线程
            //subscribeOn 指定发布线程
            .subscribeOn(Schedulers.newThread())
            //多个发布线程只会使用第一个，即下面这个无效
            .subscribeOn(Schedulers.io())

            //observeOn 指定观察线程
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                DLog.d("doOnNext: $it threadName = ${Thread.currentThread().name}")
            }

            //subscribe跟最后一个观察线程, 即切换观察线程需要调用subscribe或其他接收函数
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(disposable: Disposable) {
                    //1.
                    //当observable调用subscribe后，Observer的onSubscribe最先执行，然后才执行Observable的subscribe
                    DLog.d("onSubscribe: threadName = ${Thread.currentThread().name}")
                    mDisposable = disposable
                    mDisposableContainer?.add(disposable)
                }

                override fun onNext(integer: Int) {
                    //3.
                    DLog.d("onNext: $integer")
                    DLog.d("onNext: threadName = ${Thread.currentThread().name}")
                    //当调用Disposable.dispose()后，不再接收onNext, 但Observable依然可以发送
                    mDisposable?.dispose()
                }

                override fun onError(e: Throwable) {
                    DLog.d("onError: threadName = ${Thread.currentThread().name}")
                }

                override fun onComplete() {
                    DLog.d("onComplete: threadName = ${Thread.currentThread().name}")
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposableContainer?.clear()
    }
}