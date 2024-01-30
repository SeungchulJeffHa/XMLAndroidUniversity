package com.example.xmlandroiduniversity.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        ExcelFileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoomDb : RoomDatabase() {

    abstract fun excelFileDao(): ExcelFileDao

    companion object {
        @Volatile
        private var roomDb: RoomDb? = null

        operator fun invoke(context: Context) = roomDb ?: synchronized(this) {
            roomDb ?: buildDataBase(context).also {
                roomDb = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context,
            RoomDb::class.java,
            "DATA_BASE"
        ).addCallback(object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }).fallbackToDestructiveMigration().build()
    }
}
