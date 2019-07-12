package com.allever.android.sample.rxjava

import android.os.Bundle
import com.allever.android.sample.R
import com.allever.lib.common.app.BaseActivity
import com.allever.lib.common.util.DLog
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class RxTestActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_test)

        val threadName = Thread.currentThread().name
        DLog.d("onCreate mainThread: pid = $threadName")

        Observable.create(ObservableOnSubscribe<Int> {
            DLog.d("subscribe: threadName = ${Thread.currentThread().name}")
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
        }).subscribe(object : Observer<Int> {
            override fun onSubscribe(disposable: Disposable) {
                DLog.d("onSubscribe: threadName = ${Thread.currentThread().name}")
            }

            override fun onNext(integer: Int) {
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