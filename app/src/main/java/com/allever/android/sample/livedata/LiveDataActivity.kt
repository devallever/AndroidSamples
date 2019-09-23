package com.allever.android.sample.livedata

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.allever.android.sample.R
import com.allever.lib.common.app.BaseActivity
import kotlinx.android.synthetic.main.activity_live_data.*

class LiveDataActivity: BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnChangeName -> {
                model.currentName.value = System.currentTimeMillis().toString()
            }
        }
    }

    private lateinit var model: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        model = ViewModelProviders.of(this).get(NameViewModel::class.java)

        // Create the observer which updates the UI.
        val nameObserver = Observer<String> { newName ->
            // Update the UI, in this case, a TextView.
            nameTextView.text = newName
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.currentName.observe(this, nameObserver)

        btnChangeName.setOnClickListener(this)


    }
}