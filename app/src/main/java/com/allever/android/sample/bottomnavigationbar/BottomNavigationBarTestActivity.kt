package com.allever.android.sample.bottomnavigationbar

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.allever.android.sample.R
import com.allever.lib.common.app.BaseActivity

class BottomNavigationBarTestActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        hideNavigationBar()
//        hideNavigationBar2()
        hideNavigationBar3()

        setContentView(R.layout.activity_bottom_navigation_bar_test)
    }

    private fun hideNavigationBar3() {
        //1. 仅有 View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 上滑显示导航栏1s, 然后消失

        /*
        2.  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION + View.SYSTEM_UI_FLAG_IMMERSIVE
            上滑显示导航栏，100毫秒消失
         */
        val window = window
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
    }

    /***
    View.SYSTEM_UI_FLAG_VISIBLE：显示状态栏，Activity不全屏显示(恢复到有状态的正常情况)。
    View.INVISIBLE：隐藏状态栏，同时Activity会伸展全屏显示。
    View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    View.SYSTEM_UI_LAYOUT_FLAGS：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
    View.SYSTEM_UI_FLAG_LOW_PROFILE：状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
    */
    private fun  hideNavigationBar2() {
        //1.仅有 View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 上滑时会显示导航栏，并且不会消失

        /*
        2. View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  +
            View.SYSTEM_UI_FLAG_IMMERSIVE 的效果同1
        */

        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun hideNavigationBar() {
        //屏蔽虚拟按钮, 上滑动不会显示导航栏
        window.decorView.systemUiVisibility = View.GONE
    }

}