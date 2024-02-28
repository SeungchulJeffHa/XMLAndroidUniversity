package com.example.xmlandroiduniversity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "excel_file")
data class ExcelFileEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "createdAt") val createdAt: String,
//    @ColumnInfo(name = "isSelected") var isSelected: Boolean,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var stationId: Long = 0
)

