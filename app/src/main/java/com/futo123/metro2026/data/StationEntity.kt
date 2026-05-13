package com.futo123.metro2026.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val lineId: Int,
    val shortDescription: String,
    val history: String,
    val imageResName: String
)