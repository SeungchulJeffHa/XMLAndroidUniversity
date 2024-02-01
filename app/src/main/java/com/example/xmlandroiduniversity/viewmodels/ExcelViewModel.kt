package com.example.xmlandroiduniversity.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.xmlandroiduniversity.data.SubwayData

class ExcelViewModel : ViewModel() {

    var filename: String? = null
    var subwayData = mutableListOf<SubwayData>()
    var selectedIDs = mutableListOf<Int>()
    var horizontalScrollID: MutableList<Int> = mutableListOf()

    var colCount: Int = 1
    var rowCount: Int = 1

    fun resetCellCount() {
        colCount = 1
        rowCount = 1
    }

}