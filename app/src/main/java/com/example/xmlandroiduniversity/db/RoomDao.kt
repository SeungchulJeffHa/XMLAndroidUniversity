package com.example.xmlandroiduniversity.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExcelFileDao {

    @Query("SELECT * FROM excel_file")
    suspend fun select(): List<ExcelFileEntity>

    @Insert
    suspend fun insert(entity: ExcelFileEntity)

    @Query("DELETE FROM excel_file")
    suspend fun delete()

}