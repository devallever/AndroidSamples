package com.allever.android.sample.databinding

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import com.allever.allsample.BaseActivity
import com.allever.android.sample.R
import com.allever.android.sample.databinding.bean.User
import com.allever.lib.common.util.ToastUtils
import com.allever.lib.common.util.log.LogUtils

class DataBindingActivity : BaseActivity() {
    private var mBinding: ActivityDataBindingTestBinding? = null
    private val user = User("allever", ObservableInt(123))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding  = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_test)
        mBinding?.user = user

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.name -> {
                val id = user.id?.get()?.plus(1)
                user.name = "change name"
            }
            R.id.id -> {
                val id = user.id?.get()?.plus(1)
                user.id?.get()?.plus(1)?.let { user.id?.set(it) }
                user?.id?.toString()?.let { LogUtils.d(it) }
            }
        }
    }

}