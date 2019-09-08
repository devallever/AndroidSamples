package com.allever.android.sample.databinding

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.allever.allsample.BaseActivity
import com.allever.android.sample.R
import com.allever.android.sample.databinding.bean.User

class DataBindingActivity : BaseActivity() {
    private var mBinding: ActivityDataBindingTestBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding  = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_test)
        val user = User()
        user.id?.set(123)
        user.name = "allever"
        mBinding?.user = user

    }

}