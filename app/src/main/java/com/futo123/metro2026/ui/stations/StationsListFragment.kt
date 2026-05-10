package com.futo123.metro2026.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.futo123.metro2026.MyApplication
import com.futo123.metro2026.R
import com.futo123.metro2026.databinding.FragmentStationsListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.futo123.metro2026.data.MetroLine

class StationsListFragment : Fragment() {
    private var _binding: FragmentStationsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStationsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as MyApplication
        val repository = app.stationRepository

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Загружаем все станции, группируем по линиям и отображаем
        lifecycleScope.launch(Dispatchers.IO) {
            val allStations = repository.getAllStations()
            // Группировка по lineId (1-6)
            val lines = listOf(
                MetroLine(1, "Кировско-Выборгская", R.color.line_red, allStations.filter { it.lineId == 1 }),
                MetroLine(2, "Московско-Петроградская", R.color.line_blue, allStations.filter { it.lineId == 2 }),
                MetroLine(3, "Невско-Василеостровская", R.color.line_green, allStations.filter { it.lineId == 3 }),
                MetroLine(4, "Правобережная", R.color.line_orange, allStations.filter { it.lineId == 4 }),
                MetroLine(5, "Фрунзенско-Приморская", R.color.line_purple, allStations.filter { it.lineId == 5 }),
                MetroLine(6, "Красносельско-Калининская", R.color.line_brown, allStations.filter { it.lineId == 6 })
            )

            // Переключаемся на главный поток для работы с UI
            launch(Dispatchers.Main) {
                binding.recyclerView.adapter = StationsExpandableAdapter(lines) { station ->
                    val bundle = Bundle().apply { putInt("stationId", station.id) }
                    findNavController().navigate(R.id.action_stations_to_detail, bundle)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}