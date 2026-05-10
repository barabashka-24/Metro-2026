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
                    // Устанавливаем изображение
                    binding.stationImage.setImageResource(station.imageResId)

                    // Название станции
                    binding.stationName.text = Html.fromHtml(station.name, Html.FROM_HTML_MODE_LEGACY)

                    // Описание (поддерживаем HTML, если history содержит теги)
                    binding.stationHistory.text =
                        Html.fromHtml(station.fullHistory, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    // Если станция не найдена, можно показать ошибку или просто вернуться назад
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