package com.barabashka_24.metro2026.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trains")
data class TrainEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val history: String,
    val imageResName: String
)