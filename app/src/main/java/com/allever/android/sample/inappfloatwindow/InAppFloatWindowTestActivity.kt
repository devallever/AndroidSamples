package com.allever.android.sample.inappfloatwindow

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import com.allever.android.sample.R
import com.allever.lib.common.app.BaseActivity

class InAppFloatWindowTestActivity: BaseActivity(), View.OnClickListener {


    private lateinit var mBtnOpenFloatWindow: Button
    private lateinit var mBtnCloseFloatWindow: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_app_float_window_test)

        initView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_OPEN_FLOAT_SETTING) {

        }
    }

    private fun initView() {
        mBtnOpenFloatWindow = findViewById(R.id.in_app_float_window_main_btn_open)
        mBtnCloseFloatWindow = findViewById(R.id.in_app_float_window_main_btn_close)
        mBtnOpenFloatWindow.setOnClickListener(this)
        mBtnCloseFloatWindow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            mBtnOpenFloatWindow -> {
                val hasPermission = hasFloatPermission(this)
                if (hasPermission) {
                    //添加
                    showFloatWindow()
                } else {
                    //打开设置
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                        intent.data = Uri.parse("package:$packageName")
                        startActivityForResult(intent, REQUEST_CODE_OPEN_FLOAT_SETTING)
                    } else {

                    }
                }
            }
            mBtnCloseFloatWindow -> {

            }
        }
    }

    private fun showFloatWindow() {
        val mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams.format = PixelFormat.RGBA_8888
        mLayoutParams.flags = (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        mLayoutParams.windowAnimations = 0
        val imageView = ImageView(this)
        imageView.setImageResource(R.mipmap.ic_launcher)
        mWindowManager.addView(imageView, mLayoutParams)
    }

    private fun hasFloatPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else {
            hasPermissionBelowMarshmallow(context)
        }
    }

    private fun hasPermissionBelowMarshmallow(context: Context): Boolean {
        return try {
            val manager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val dispatchMethod = AppOpsManager::class.java.getMethod(
                "checkOp",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                String::class.java
            )
            //AppOpsManager.OP_SYSTEM_ALERT_WINDOW = 24
            AppOpsManager.MODE_ALLOWED == dispatchMethod.invoke(
                manager, 24, Binder.getCallingUid(), context.applicationContext.packageName
            ) as Int
        } catch (e: Exception) {
            false
        }

    }

    companion object {
        private const val REQUEST_CODE_OPEN_FLOAT_SETTING = 0x01
    }
}