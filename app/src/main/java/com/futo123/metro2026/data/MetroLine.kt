package com.futo123.metro2026.data

import com.futo123.metro2026.R

data class MetroLine(
    val id: Int,
    val name: String,
    val color: Int,
    val stations: List<Station>
)

object MetroLineColors {
    fun getColor(lineId: Int): Int {
        return when (lineId) {
            1 -> R.color.line_red
            2 -> R.color.line_blue
            3 -> R.color.line_green
            4 -> R.color.line_orange
            5 -> R.color.line_purple
            6 -> R.color.line_brown
            else -> android.R.color.black
        }
    }
}