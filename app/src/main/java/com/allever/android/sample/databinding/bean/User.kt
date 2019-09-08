package com.allever.android.sample.databinding.bean

import androidx.databinding.ObservableInt


class User(name: String, id: ObservableInt) {
    var name: String? = name
    var id: ObservableInt? = id
}