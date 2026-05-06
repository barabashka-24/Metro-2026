package com.futo123.metro2026dp.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.futo123.metro2026dp.data.Repository
import com.futo123.metro2026dp.databinding.FragmentMapBinding
import com.futo123.metro2026dp.R

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
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

    inner class WebAppInterface {
        @JavascriptInterface
        fun showStation(name: String) {
            Log.d("MapFragment", "Station clicked: $name")
            val station = Repository.stations.firstOrNull { it.name.equals(name, ignoreCase = true) }
            if (station != null) {
                activity?.runOnUiThread {
                    val bundle = Bundle().apply { putInt("stationId", station.id) }
                    findNavController().navigate(R.id.action_map_to_station_detail, bundle)
                }
            } else {
                Log.e("MapFragment", "Station not found: $name")
            }
        }
    }
}