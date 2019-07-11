package com.allever.android.sample.retrofit.bean

class PrintData {
    var type: Int = 0
    var text: String = ""
    val config: PrintConfig? = null
}

class PrintConfig {
    var blob: Int = 0
    var align: Int = 0
    var textScale: Int = 0
    var underLine: Int = 0
    var italic: Int = 0
    var lineSpace: Int = 40
    var textSpace: Int = 0
    var leftMargin: Int = 0
    var rightMargin: Int = 0
    var qrLeftMargin: Int = 0
    var qrScale: Int = 8
    var imageUrl: String = ""
    var tableIndexList: MutableList<Int>? = null
    var tableColumn: MutableList<TableColumn>? = null
}

class TableColumn {
    var data: MutableList<String>? = null
}