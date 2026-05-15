package com.barabashka_24.metro2026

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.barabashka_24.metro2026.data.MetroDatabase
import com.barabashka_24.metro2026.data.StationRepository
import com.barabashka_24.metro2026.data.TrainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication : Application() {
    lateinit var database: MetroDatabase
    lateinit var stationRepository: StationRepository
    lateinit var trainRepository: TrainRepository

    override fun onCreate() {
        super.onCreate()

        // Применяем тему
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val themeMode = prefs.getInt("theme_mode", 0) // 0 - светлая, 1 - тёмная, 2 - как в системе
        val nightMode = when (themeMode) {
            0 -> AppCompatDelegate.MODE_NIGHT_NO
            1 -> AppCompatDelegate.MODE_NIGHT_YES
            2 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)

        // Инициализация бд
        database = MetroDatabase.getDatabase(this)
        stationRepository = StationRepository(database, this)
        trainRepository = TrainRepository(database, this)

        CoroutineScope(Dispatchers.IO).launch {
            stationRepository.updateStationsIfNeeded(this@MyApplication)
            trainRepository.updateTrainsIfNeeded(this@MyApplication)
        }


    }
}