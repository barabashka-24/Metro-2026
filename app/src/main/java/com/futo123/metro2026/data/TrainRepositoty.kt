package com.futo123.metro2026.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.Gson

data class TrainsData(
    val version: Int,
    val trains: List<TrainEntity>
)

class TrainRepository(private val database: MetroDatabase, private val context: Context) {

    suspend fun getAllTrains(): List<Train> = withContext(Dispatchers.IO) {
        database.trainDao().getAll().map { it.toTrain() }
    }

    suspend fun getTrainById(id: Int): Train? = withContext(Dispatchers.IO) {
        database.trainDao().getById(id)?.toTrain()
    }

    suspend fun updateTrainsIfNeeded(context: Context) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val lastVersion = prefs.getInt("trains_version", 0)

        val json = context.assets.open("trains.json").bufferedReader().use { it.readText() }
        val data = Gson().fromJson(json, TrainsData::class.java)

        if (data.version > lastVersion) {
            database.trainDao().deleteAll()
            database.trainDao().insertAll(data.trains)
            prefs.edit().putInt("trains_version", data.version).apply()
        }
    }

    private fun TrainEntity.toTrain(): Train {
        val drawableId = context.resources.getIdentifier(imageResName, "drawable", context.packageName)
        return Train(id, name, description, history, drawableId)
    }
}