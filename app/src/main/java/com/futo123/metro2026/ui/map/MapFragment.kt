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

        // СНАЧАЛА загружаем станции в кэш
        lifecycleScope.launch(Dispatchers.IO) {
            stationsCache = repository.getAllStations()
            Log.d("MapFragment", "Stations cached: ${stationsCache.size}")

            // ПОТОМ на главном потоке настраиваем WebView
            launch(Dispatchers.Main) {
                binding.webView.apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    settings.builtInZoomControls = true
                    settings.displayZoomControls = false
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true
                    isHorizontalScrollBarEnabled = false
                    isVerticalScrollBarEnabled = false
                    addJavascriptInterface(WebAppInterface(), "Android")
                    loadUrl("file:///android_asset/metro_map.html")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun showStation(stationIdStr: String) {
            // Преобразуем строку в число (ID станции)
            val stationId = stationIdStr.toIntOrNull() ?: return
            // Ищем станцию по ID в кэше
            val station = stationsCache.firstOrNull { it.id == stationId }
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
                Log.e("MapFragment", "Station not found for id: $stationId")
            }
        }
    }
}