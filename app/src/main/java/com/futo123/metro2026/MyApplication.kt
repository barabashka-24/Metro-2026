package com.futo123.metro2026

import android.app.Application
import com.futo123.metro2026.data.MetroDatabase
import com.futo123.metro2026.data.StationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication : Application() {
    lateinit var database: MetroDatabase
    lateinit var stationRepository: StationRepository

    override fun onCreate() {
        super.onCreate()
        database = MetroDatabase.getDatabase(this)
        stationRepository = StationRepository(database, this)

        CoroutineScope(Dispatchers.IO).launch {
            stationRepository.updateStationsIfNeeded(this@MyApplication)
        }
    }
}