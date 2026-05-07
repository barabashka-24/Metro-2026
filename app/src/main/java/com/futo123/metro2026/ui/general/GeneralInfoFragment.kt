package com.futo123.metro2026.ui.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.futo123.metro2026.databinding.FragmentGeneralInfoBinding

class GeneralInfoFragment : Fragment() {
    private var _binding: FragmentGeneralInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGeneralInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textInfo.text = "Петербургский метрополитен открыт 15 ноября 1955 года. " +
                "Первая линия проходила от «Автово» до «Площади Восстания».\n\n" +
                "В настоящее время это один из красивейших метрополитенов мира, " +
                "известный своими архитектурными решениями и глубокими станциями."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}