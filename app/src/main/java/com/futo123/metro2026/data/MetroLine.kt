package com.barabashka_24.metro2026.data

data class MetroLine(
    val id: Int,
    val name: String,
    val icon: Int,
    val stations: List<Station>
)