package com.futo123.metro2026.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [StationEntity::class], version = 1, exportSchema = false)
abstract class MetroDatabase : RoomDatabase() {

    abstract fun stationDao(): StationDao

    // 📦 Вложенный класс обратного вызова
    class PrepopulateCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                // Получаем базу данных через companion
                val database = getDatabase(context)
                val stationDao = database.stationDao()
                val json = context.assets.open("stations.json").bufferedReader().use { it.readText() }
                val type = object : com.google.gson.reflect.TypeToken<List<StationEntity>>() {}.type
                val stations: List<StationEntity> = com.google.gson.Gson().fromJson(json, type)
                stationDao.insertAll(stations)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MetroDatabase? = null

        fun getDatabase(context: Context): MetroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetroDatabase::class.java,
                    "metro_db"
                )
                    .addCallback(PrepopulateCallback(context))   // 👈 теперь корректно
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}