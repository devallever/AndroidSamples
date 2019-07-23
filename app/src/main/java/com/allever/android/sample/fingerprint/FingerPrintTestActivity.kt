package com.allever.android.sample.fingerprint

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.allever.android.sample.R
import com.allever.lib.common.app.BaseActivity
import com.allever.lib.common.util.ToastUtils

class FingerPrintTestActivity : BaseActivity() {
    private lateinit var mBtnStart: Button
    private lateinit var mTvInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finger_print_test)

        initView()

        FingerPrintHelper.init(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        FingerPrintHelper.release()
    }

    private fun initView() {
        mBtnStart = findViewById(R.id.finger_print_btn_start)
        mBtnStart.setOnClickListener {

            //1. 初始化
            //2. 检查硬件
            val hardwareSupport = FingerPrintHelper.hardwareSupport()
            if (!hardwareSupport) {
                ToastUtils.show("硬件不支持")
                return@setOnClickListener
            }
            //3. 检查存在指纹
            val hasFingerPrinter = FingerPrintHelper.hasFingerPrints()
            if (hardwareSupport) {
                ToastUtils.show("有可用的指纹")
            } else {
                ToastUtils.show("没有可用的指纹")
                return@setOnClickListener
            }
            //4. 检查是否可用
            //5. 设置回调
            //6. 开启扫描器
            FingerPrintHelper.authenticate(object : FingerPrintHelper.AuthenticateCallback {
                override fun onAuthenticationError() {
                    ToastUtils.show("验证错误")
                }

                override fun onAuthenticationSucceeded() {
                    ToastUtils.show("验证成功")
                }

                override fun onAuthenticationFailed() {
                    ToastUtils.show("验证失败")
                }

            })
        }

        mTvInfo = findViewById(R.id.finger_print_tv_info)
    }

}