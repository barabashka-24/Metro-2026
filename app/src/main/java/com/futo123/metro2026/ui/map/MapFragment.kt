package com.futo123.metro2026.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.futo123.metro2026.MyApplication
import com.futo123.metro2026.R
import com.futo123.metro2026.data.Station
import com.futo123.metro2026.databinding.FragmentMapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    // Кэш всех станций для быстрого поиска при кликах на карте
    private var stationsCache: List<Station> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = (requireActivity().application as MyApplication)
        val repository = app.stationRepository

        // Загружаем все станции из базы данных и кэшируем
        lifecycleScope.launch(Dispatchers.IO) {
            stationsCache = repository.getAllStations()
            Log.d("MapFragment", "Stations loaded: ${stationsCache.size}")
        }

        // Настройка WebView
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = true   // жесты масштабирования
            settings.displayZoomControls = false  // скрываем кнопки +/- (если не нужны)
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            addJavascriptInterface(WebAppInterface(), "Android")
            loadUrl("file:///android_asset/metro_map.html")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Интерфейс для вызовов из JavaScript.
     * Метод showStation вызывается при клике на станцию на карте.
     */
    inner class WebAppInterface {
        @JavascriptInterface
        fun showStation(name: String) {
            Log.d("MapFragment", "Clicked station: $name")
            // Ищем станцию в кэше (без учёта регистра)
            val station = stationsCache.firstOrNull {
                it.name.equals(name, ignoreCase = true)
            }
            if (station != null) {
                activity?.runOnUiThread {
                    val bundle = Bundle().apply {
                        putInt("stationId", station.id)
                    }
                    findNavController().navigate(
                        R.id.action_map_to_station_detail,
                        bundle
                    )
                }
            } else {
                Log.e("MapFragment", "Station not found: $name")
                // Можно показать Toast, но в фрагменте проще использовать Snackbar
                // или ничего не делать.
            }
        }
    }
}