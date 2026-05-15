package com.barabashka_24.metro2026.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.barabashka_24.metro2026.MyApplication
import com.barabashka_24.metro2026.R
import com.barabashka_24.metro2026.databinding.FragmentStationsListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.barabashka_24.metro2026.data.MetroLine

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

        lifecycleScope.launch(Dispatchers.IO) {
            val allStations = repository.getAllStations()
            val lines = listOf(
                MetroLine(1, "Кировско - Выборгская", R.drawable.stations_line_1_icon, allStations.filter { it.lineId == 1 }),
                MetroLine(2, "Московско - Петроградская", R.drawable.stations_line_2_icon, allStations.filter { it.lineId == 2 }),
                MetroLine(3, "Невско - Василеостровская", R.drawable.stations_line_3_icon, allStations.filter { it.lineId == 3 }),
                MetroLine(4, "Лахтинско - Правобережная", R.drawable.stations_line_4_icon, allStations.filter { it.lineId == 4 }),
                MetroLine(5, "Фрунзенско - Приморская", R.drawable.stations_line_5_icon, allStations.filter { it.lineId == 5 }),
                MetroLine(6, "Красносельско - Калининская", R.drawable.stations_line_6_icon, allStations.filter { it.lineId == 6 })
            )

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