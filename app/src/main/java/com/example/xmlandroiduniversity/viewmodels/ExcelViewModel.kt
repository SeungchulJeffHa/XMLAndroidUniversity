package com.example.xmlandroiduniversity.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.xmlandroiduniversity.R
import com.example.xmlandroiduniversity.db.ExcelFileEntity
import com.example.xmlandroiduniversity.global.Constant

class ExcelViewModel : ViewModel() {

    var filename: String? = null

    var viewMode = Constant.READMODE

    //    var subwayData = mutableListOf<SubwayData>()
//    var selectedIDs = mutableListOf<Int>()
    var horizontalScrollID: MutableList<Int> = mutableListOf()

    var colCount: Int = 1
    var rowCount: Int = 1

    fun resetCellCount() {
        colCount = 1
        rowCount = 1
    }

    fun moveToReadPage(data: ExcelFileEntity, navController: NavController) {
        filename = data.name
        navController.navigate(R.id.action_poiFragment_to_viewExcelFragment)
    }

}