package com.example.xmlandroiduniversity.data.poi

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey

//@Dao
interface ExcelDao {
    @Insert
    suspend fun createExcel()
}

@Entity(tableName = "subway_station")
data class SubwayData(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "line") val line: Int,
    @ColumnInfo(name = "order") val order: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var stationId: Long = 0
)

