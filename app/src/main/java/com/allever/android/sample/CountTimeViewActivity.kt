package com.allever.android.sample

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.allever.lib.common.app.BaseActivity
import com.allever.lib.common.util.log
import kotlinx.android.synthetic.main.activity_count_time.*

class CountTimeViewActivity: BaseActivity(), View.OnClickListener {

    private val mCountTimeView = CountTimeView(10 * 1000, 1000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_time)

        btn_count_time_start.setOnClickListener(this)
        btn_count_time_stop.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_count_time_start -> {
                mCountTimeView.startTimer()
            }

            R.id.btn_count_time_stop -> {
                mCountTimeView.cancel()
            }
        }
    }

    class CountTimeView(duration: Long, interval: Long): CountDownTimer(duration, interval) {
        override fun onFinish() {
            log("计时结束")
        }

        override fun onTick(millisUntilFinished: Long) {
            log("语音识别倒计时" + "剩余 " + millisUntilFinished / 1000 + "秒")
        }

        fun startTimer() {
            cancel()
            start()
        }
    }
}