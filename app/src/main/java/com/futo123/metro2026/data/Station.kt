package com.futo123.metro2026.data

data class Station(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val fullHistory: String,
    val imageResId: Int // ресурс изображения
)