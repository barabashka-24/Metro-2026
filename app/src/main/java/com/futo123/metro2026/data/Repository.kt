package com.futo123.metro2026.data

import com.futo123.metro2026.R

object Repository {

    val trains = listOf(
        Train(0, "Ем", "Метровагоны типа Ем", "Эксплуатируются с 1960-х...", R.drawable.train_em_outside),
        Train(1, "81-717/714", "Самые массовые вагоны", "История модели 81-717...", R.drawable.train_81_717_outside),
        // Добавь остальные
    )


    fun getTrainById(id: Int) = trains.firstOrNull { it.id == id }
}