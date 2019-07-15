package com.allever.android.sample.bottomnavigationbar

import android.os.Bundle
import android.view.View
import com.allever.android.sample.R
import com.allever.lib.common.app.BaseActivity

class BottomNavigationBarTestActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideNavigationBar()

        setContentView(R.layout.activity_bottom_navigation_bar_test)
    }

    private fun hideNavigationBar() {
        //屏蔽虚拟按钮, 上滑动不会显示导航栏
        window.decorView.systemUiVisibility = View.GONE
    }
}