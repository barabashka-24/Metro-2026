package com.futo123.metro2026dp.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.futo123.metro2026dp.data.Repository
import com.futo123.metro2026dp.databinding.FragmentStationDetailBinding

class StationDetailFragment : Fragment() {
    private var _binding: FragmentStationDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем stationId из аргументов
        val stationId = arguments?.getInt("stationId") ?: -1
        val station = Repository.getStationById(stationId)

        if (station != null) {
            binding.stationImage.setImageResource(station.imageResId)
            binding.stationName.text = station.name
            binding.stationHistory.text = station.fullHistory
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}