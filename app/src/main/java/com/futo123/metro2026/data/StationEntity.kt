package com.futo123.metro2026.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val lineId: Int,            // 1-Кировско-Выборгская, 2-Московско-Петроградская и т.д.
    val shortDescription: String,
    val history: String,        // можно хранить HTML для форматирования
    val imageResName: String    // имя файла картинки, например "avtovo"
)