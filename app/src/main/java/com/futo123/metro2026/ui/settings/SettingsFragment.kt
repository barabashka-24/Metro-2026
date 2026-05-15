package com.barabashka_24.metro2026.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.barabashka_24.metro2026.databinding.FragmentSettingsBinding
import com.barabashka_24.metro2026.R
import android.widget.SeekBar

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val PREF_FONT_SIZE = "pref_font_size"
        const val DEFAULT_FONT_SIZE = 16f
        const val PREF_THEME_MODE = "theme_mode"
        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
        const val THEME_SYSTEM = 2

        const val PREF_VOLUME = "pref_volume"
        const val DEFAULT_VOLUME = 50
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Настройка размера шрифта
        val currentSize = prefs.getFloat(PREF_FONT_SIZE, DEFAULT_FONT_SIZE)
        binding.fontSizeSeekBar.progress = ((currentSize - 14f) / 2).toInt()
        updateSampleText(currentSize)

        binding.fontSizeSeekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                val newSize = 14f + progress * 2
                updateSampleText(newSize)
                prefs.edit().putFloat(PREF_FONT_SIZE, newSize).apply()
            }
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })

        // Настройка темы
        when (prefs.getInt(PREF_THEME_MODE, THEME_LIGHT)) {
            THEME_LIGHT -> binding.themeLight.isChecked = true
            THEME_DARK -> binding.themeDark.isChecked = true
            THEME_SYSTEM -> binding.themeSystem.isChecked = true
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val mode = when (checkedId) {
                R.id.themeLight -> THEME_LIGHT
                R.id.themeDark -> THEME_DARK
                R.id.themeSystem -> THEME_SYSTEM
                else -> THEME_LIGHT
            }
            prefs.edit().putInt(PREF_THEME_MODE, mode).apply()

            // Применяем тему немедленно (с пересозданием активности)
            val nightMode = when (mode) {
                THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
                THEME_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)
            // Перезапуск активности для полного применения
            requireActivity().recreate()
        }
        val currentVolume = prefs.getInt(PREF_VOLUME, DEFAULT_VOLUME)
        binding.soundVolumeSeekBar.progress = currentVolume
        updateVolumeLabel(currentVolume)

        binding.soundVolumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateVolumeLabel(progress)
                prefs.edit().putInt(PREF_VOLUME, progress).apply()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateSampleText(size: Float) {
        binding.sampleText.textSize = size
        binding.currentSizeLabel.text = getString(R.string.current_font_size, size.toInt())
    }

    private fun updateVolumeLabel(progress: Int) {
        binding.currentVolumeLabel.text = getString(R.string.current_volume_level, progress)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}