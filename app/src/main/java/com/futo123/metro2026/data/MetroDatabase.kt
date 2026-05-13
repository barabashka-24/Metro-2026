package com.futo123.metro2026.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [StationEntity::class, TrainEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MetroDatabase : RoomDatabase() {

    abstract fun stationDao(): StationDao
    abstract fun trainDao(): TrainDao

    companion object {
        @Volatile
        private var INSTANCE: MetroDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `trains` (
                        `id` INTEGER NOT NULL,
                        `name` TEXT NOT NULL,
                        `description` TEXT NOT NULL,
                        `history` TEXT NOT NULL,
                        `imageResName` TEXT NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                """)
            }
        }

        fun getDatabase(context: Context): MetroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetroDatabase::class.java,
                    "metro_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}