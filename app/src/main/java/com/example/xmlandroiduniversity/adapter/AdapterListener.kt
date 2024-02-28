package com.example.xmlandroiduniversity.adapter

import com.example.xmlandroiduniversity.data.home.MenuItem
import com.example.xmlandroiduniversity.db.ExcelFileEntity

interface POIAdapterListener {
    fun onListItemPressed(data: ExcelFileEntity) = Unit
    fun onListItemCheckboxPressed(data: ExcelFileEntity) = Unit
}

interface HomeGridAdapterListener {
    fun onListItemPressed(data: MenuItem) = Unit
}