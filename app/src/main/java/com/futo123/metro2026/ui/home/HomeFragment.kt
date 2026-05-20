package com.barabashka_24.metro2026.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.barabashka_24.metro2026.databinding.FragmentHomeBinding
import com.barabashka_24.metro2026.R
import android.media.MediaPlayer
import android.content.Context
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMenu.setOnClickListener { menuView ->
            menuView.animate().cancel()
            menuView.rotation = 0f
            menuView.animate()
                .rotationBy(360f)
                .setDuration(400)
                .start()
            findNavController().navigate(R.id.action_home_to_menu)
        }

        binding.btnSettings.setOnClickListener { settingsView ->
            settingsView.animate().cancel()
            settingsView.rotation = 0f
            settingsView.animate()
                .rotationBy(360f)
                .setDuration(400)
                .start()
            findNavController().navigate(R.id.action_home_to_settings)
        }




        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.home_metro_spb_logo).apply {}
        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val volume = prefs.getInt("pref_volume", 100) / 100f  // 0.0 .. 1.0

        mediaPlayer?.setVolume(volume, volume)
        binding.homeMetroSpbLogo.setOnClickListener {playBridgeSound()}
    }

    private fun playBridgeSound() {
        mediaPlayer?.let { mp ->
            if (mp.isPlaying) {
                mp.seekTo(0)        // Перематываем в начало, если уже играет
                mp.start()
            } else {
                mp.start()          // Начинаем воспроизведение
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Остановка и освобождение MediaPlayer
        mediaPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        mediaPlayer = null
        _binding = null
    }
}