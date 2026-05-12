package com.futo123.metro2026.ui.stations

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futo123.metro2026.data.MetroLine
import com.futo123.metro2026.data.Station
import com.futo123.metro2026.databinding.ItemLineHeaderBinding
import com.futo123.metro2026.databinding.ItemStationBinding

class StationsExpandableAdapter(
    private val lines: List<MetroLine>,
    private val onStationClick: (Station) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Плоский список: изначально только заголовки линий
    private val items = mutableListOf<Any>()
    // ID раскрытых линий
    private val expandedLines = mutableSetOf<Int>()

    companion object {
        private const val TYPE_LINE = 0
        private const val TYPE_STATION = 1
    }

    init {
        // Строим начальный список – только заголовки
        items.addAll(lines)
    }

    fun toggleLine(lineId: Int) {
        val lineIndex = items.indexOfFirst { it is MetroLine && it.id == lineId }
        if (lineIndex == -1) return

        val line = items[lineIndex] as MetroLine
        if (expandedLines.contains(lineId)) {
            // СВОРАЧИВАЕМ: удаляем станции из списка
            val stationCount = line.stations.size
            items.removeAll(line.stations)
            expandedLines.remove(lineId)

            // Уведомляем об удалении элементов (станций)
            notifyItemRangeRemoved(lineIndex + 1, stationCount)
            // Обновляем заголовок (стрелочка)
            notifyItemChanged(lineIndex)
        } else {
            // РАСКРЫВАЕМ: добавляем станции сразу после заголовка
            items.addAll(lineIndex + 1, line.stations)
            expandedLines.add(lineId)

            // Уведомляем о добавлении элементов
            notifyItemRangeInserted(lineIndex + 1, line.stations.size)
            // Обновляем заголовок
            notifyItemChanged(lineIndex)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MetroLine -> TYPE_LINE
            is Station -> TYPE_STATION
            else -> throw IllegalArgumentException()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LINE -> {
                val binding = ItemLineHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                LineViewHolder(binding)
            }
            TYPE_STATION -> {
                val binding = ItemStationBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                StationViewHolder(binding)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is MetroLine -> {
                val lineHolder = holder as LineViewHolder
                val isExpanded = expandedLines.contains(item.id)
                lineHolder.bind(item, isExpanded)
                // Обработчик клика теперь на уровне элемента
                lineHolder.itemView.setOnClickListener { toggleLine(item.id) }
            }
            is Station -> {
                val stationHolder = holder as StationViewHolder
                stationHolder.bind(item)
                stationHolder.itemView.setOnClickListener { onStationClick(item) }
            }
        }
    }

    override fun getItemCount() = items.size

    class LineViewHolder(private val binding: ItemLineHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(line: MetroLine, isExpanded: Boolean) {
            binding.lineName.text = line.name
            binding.lineColorBar.setImageResource(line.icon)
            binding.expandIcon.text = if (isExpanded) "▼" else ">"
        }
    }

    class StationViewHolder(private val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(station: Station) {
            binding.stationName.text = Html.fromHtml(station.name, Html.FROM_HTML_MODE_LEGACY)
            binding.stationDesc.text = station.shortDescription
        }
    }
}