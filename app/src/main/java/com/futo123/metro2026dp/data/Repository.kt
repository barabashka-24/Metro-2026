package com.futo123.metro2026dp.data

import com.futo123.metro2026dp.R

object Repository {
    val stations = listOf(
        Station(0, "Автово", "Южный вестибюль, 1955", "Полная история Автово...", R.drawable.avtovo),
        Station(1, "Площадь Восстания", "Пересадочный узел", "История площади Восстания...", R.drawable.station_ploshad_vosstaniya),
        // Добавь все нужные станции
    )

    val trains = listOf(
        Train(0, "Ем", "Метровагоны типа Ем", "Эксплуатируются с 1960-х...", R.drawable.train_em_outside),
        Train(1, "81-717/714", "Самые массовые вагоны", "История модели 81-717...", R.drawable.train_81_717_outside),
        // Добавь остальные
    )

    fun getStationById(id: Int) = stations.firstOrNull { it.id == id }
    fun getTrainById(id: Int) = trains.firstOrNull { it.id == id }
}