package com.allever.android.sample.databinding

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.allever.allsample.BaseActivity
import com.allever.android.sample.R
import com.allever.android.sample.databinding.bean.User
import com.allever.lib.common.util.ToastUtils

class DataBindingActivity : BaseActivity() {
    private var mBinding: ActivityDataBindingTestBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding  = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_test)
        val user = User()
        user.id = 123
        user.name = "allever"
        mBinding?.user = user

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.name -> {
                ToastUtils.show(mBinding?.user?.name)
            }
            R.id.id -> {
                ToastUtils.show(mBinding?.user?.id.toString())
            }
        }
    }

}