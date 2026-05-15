package com.barabashka_24.metro2026.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.barabashka_24.metro2026.databinding.FragmentAboutBinding
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
import com.barabashka_24.metro2026.R

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textAbout.text = """
            Metro 2026
            
            Справочник по Петербургскому метрополитену
            Версия: 1.0.4
            
            ©barabashka_24  
            UI-поддержка: DanaEsko
            
        """.trimIndent()

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.bronevaya).apply {}
        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val volume = prefs.getInt("pref_volume", 100) / 100f  // 0.0 .. 1.0
        mediaPlayer?.setVolume(volume, volume)
        binding.aboutLogo.setOnClickListener {playBridgeSound()}

        binding.btnAboutTg.setOnClickListener {
            openUrl("https://t.me/metro2026spb")
        }
        binding.btnAboutVk.setOnClickListener {
            openUrl("https://vk.com/metro2026spb")
        }
        binding.btnAboutGithub.setOnClickListener {
            openUrl("https://github.com/barabashka-24/Metro2026")
        }
        binding.btnAboutMail.setOnClickListener {
            composeEmail("barabashka-24@bk.ru", "Metro 2026. Обратная связь.")
        }
        binding.btnAboutRustore.setOnClickListener {
            openUrl("https://www.rustore.ru/catalog/app/com.barabashka_24.metro2026")
        }
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Нет приложения для открытия ссылки", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun composeEmail(address: String, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // только email-клиенты
            putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Почтовый клиент не найден", Toast.LENGTH_SHORT).show()
        }
    }


    private fun playBridgeSound() {
        mediaPlayer?.let { mp ->
            if (mp.isPlaying) {
                mp.seekTo(0)
                mp.start()
            } else {
                mp.start()
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