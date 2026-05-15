package com.barabashka_24.metro2026.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.Gson

data class StationsData(
    val version: Int,
    val stations: List<StationEntity>
)

class StationRepository(
    private val database: MetroDatabase,
    private val context: Context
) {
    suspend fun getAllStations(): List<Station> = withContext(Dispatchers.IO) {
        database.stationDao().getAll().map { it.toStation() }
    }

    suspend fun getStationById(id: Int): Station? = withContext(Dispatchers.IO) {
        database.stationDao().getById(id)?.toStation()
    }

    suspend fun getStationsByLine(lineId: Int): List<Station> = withContext(Dispatchers.IO) {
        database.stationDao().getByLine(lineId).map { it.toStation() }
    }

    private fun StationEntity.toStation(): Station {
        val drawableId = getDrawableId(context, imageResName)
        return Station(id, name, shortDescription, history, drawableId, lineId)
    }

    private fun getDrawableId(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }

    suspend fun updateStationsIfNeeded(context: Context) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val lastVersion = prefs.getInt("stations_version", 0)

        val json = context.assets.open("stations.json").bufferedReader().use { it.readText() }
        val data = Gson().fromJson(json, StationsData::class.java)

        if (data.version > lastVersion) {
            database.stationDao().deleteAll()
            database.stationDao().insertAll(data.stations)
            prefs.edit().putInt("stations_version", data.version).apply()
        }
    }
}