package com.futo123.metro2026.ui.stations

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.futo123.metro2026.MyApplication
import com.futo123.metro2026.databinding.FragmentStationDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context

class StationDetailFragment : Fragment() {

    private var _binding: FragmentStationDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем ID станции из аргументов
        val stationId = arguments?.getInt("stationId") ?: -1
        if (stationId == -1) {
            findNavController().popBackStack()
            return
        }

        // Получаем репозиторий из Application
        val app = requireActivity().application as MyApplication
        val repository = app.stationRepository

        // Загружаем станцию в фоне
        lifecycleScope.launch(Dispatchers.IO) {
            val station = repository.getStationById(stationId)

            // Переключаемся на главный поток для обновления UI
            launch(Dispatchers.Main) {
                if (station != null) {
                    binding.stationImage.setImageResource(station.imageResId)
                    binding.stationName.text = Html.fromHtml(station.name, Html.FROM_HTML_MODE_LEGACY)
                    binding.stationHistory.text = Html.fromHtml(station.fullHistory, Html.FROM_HTML_MODE_LEGACY)

                    val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    val fontSize = prefs.getFloat("pref_font_size", 16f)
                    binding.stationHistory.textSize = fontSize
                } else {
                    binding.stationName.text = "Станция не найдена"
                    binding.stationHistory.text = ""
                }
            }
        }

        // Кнопка "Назад"
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}