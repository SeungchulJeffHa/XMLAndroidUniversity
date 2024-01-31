package com.example.xmlandroiduniversity.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.xmlandroiduniversity.data.SubwayData

class ExcelViewModel : ViewModel() {

    var filename: String? = null
    var subwayData = mutableListOf<SubwayData>()

    var selectedIDs = mutableListOf<Int>()

    var coordinateX = 0

    var horizontalScrollID: MutableList<Int> = mutableListOf()

    fun currentX() {
        Log.d(
            "X의 위치",
            "===============================================================${coordinateX}================================================"
        )
    }
}