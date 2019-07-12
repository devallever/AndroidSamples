package com.allever.android.sample.rxjava

import android.os.Bundle

import com.allever.android.sample.R
import com.allever.lib.common.app.BaseActivity
import com.allever.lib.common.util.DLog

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RxTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_test)

        val threadName = Thread.currentThread().name
        DLog.d("onCreate mainThread: pid = $threadName")

        //默认订阅发生的线程和观察的线程相同
        Observable.create(ObservableOnSubscribe<Int> {
            //2.
            DLog.d("subscribe: threadName = ${Thread.currentThread().name}")
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
        })
            //新建线程
            //subscribeOn 指定发布线程
            .subscribeOn(Schedulers.newThread())
            //observeOn 指定观察线程
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(disposable: Disposable) {
                    //1.
                    //当observable调用subscribe后，Observer的onSubscribe最先执行，然后才执行Observable的subscribe
                    DLog.d("onSubscribe: threadName = ${Thread.currentThread().name}")
                }

                override fun onNext(integer: Int) {
                    //3.
                    DLog.d("onNext: $integer")
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
}