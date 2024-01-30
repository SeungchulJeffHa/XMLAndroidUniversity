package com.example.xmlandroiduniversity.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExcelFileDao {

    @Query("SELECT * FROM excel_file")
    suspend fun select(): ExcelFileEntity

    @Insert
    suspend fun insert(entity: ExcelFileEntity)


}