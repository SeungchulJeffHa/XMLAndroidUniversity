package com.example.xmlandroiduniversity.viewmodels

import androidx.lifecycle.ViewModel
import com.example.xmlandroiduniversity.data.SubwayData

class ExcelViewModel : ViewModel() {

    var filename: String? = null
    var subwayData = mutableListOf<SubwayData>()

}