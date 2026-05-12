package com.futo123.metro2026.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.futo123.metro2026.databinding.FragmentAboutBinding
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textAbout.text = """
            Метро 2026
            
            Справочник по Петербургскому метрополитену
            Версия: 1.0.1_alpha
            
            ©futo123   
            
        """.trimIndent()

        binding.btnAboutTg.setOnClickListener {
            openUrl("https://t.me/metro2026spb")
        }
        binding.btnAboutVk.setOnClickListener {
            openUrl("https://vk.com/metro2026spb")
        }
        binding.btnAboutMail.setOnClickListener {
            composeEmail("ponomarev2016t@mail.ru", "Metro2026. Обратная связь.")
        }
        binding.btnAboutRustore.setOnClickListener {
            openUrl("https://www.rustore.ru")
        }
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
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
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Почтовый клиент не найден", Toast.LENGTH_SHORT).show()
        }
    }


override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}