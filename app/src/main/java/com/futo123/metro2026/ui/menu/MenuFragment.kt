package com.barabashka_24.metro2026.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.barabashka_24.metro2026.databinding.FragmentMenuBinding
import com.barabashka_24.metro2026.R
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGeneral.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_general)
        }
        binding.btnMap.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_map)
        }
        binding.btnTrains.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_trains_list)
        }
        binding.btnStations.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_stations_list)
        }
        binding.btnAbout.setOnClickListener {aboutView ->
            aboutView.animate().cancel()
            aboutView.translationY = 0f
            aboutView.animate()
                .translationYBy(-50f)
                .setDuration(600)
                .setInterpolator(DecelerateInterpolator())
                .withEndAction {
                    aboutView.animate()
                        .translationY(0f)
                        .setDuration(400)
                        .setInterpolator(OvershootInterpolator(2f))
                        .start()
                }
                .start()
            findNavController().navigate(R.id.action_menu_to_about)
        }
        binding.btnHelp.setOnClickListener {helpView ->
            helpView.animate().cancel()
            helpView.translationY = 0f
            helpView.animate()
                .translationYBy(-50f)
                .setDuration(600)
                .setInterpolator(DecelerateInterpolator())
                .withEndAction {
                    helpView.animate()
                        .translationY(0f)
                        .setDuration(400)
                        .setInterpolator(OvershootInterpolator(2f))
                        .start()
                }
                .start()
            findNavController().navigate(R.id.action_menu_to_help)
        }
        binding.menuBridge.setOnClickListener { bridgeView ->
            bridgeView.animate().cancel()
            bridgeView.rotation = 0f
            bridgeView.translationY = 0f
            bridgeView.animate()
                .rotationBy(360f)
                .translationYBy(-80f)
                .setDuration(650)
                .setInterpolator(DecelerateInterpolator())
                .withEndAction {
                    bridgeView.animate()
                        .translationY(0f)
                        .setDuration(400)
                        .setInterpolator(OvershootInterpolator(2f))
                        .start()
                }
                .start()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}