package com.futo123.metro2026dp.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.futo123.metro2026dp.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {
    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textHelp.text = """
            Как пользоваться:
            1. На главном экране нажмите «Меню» для перехода к разделам.
            2. В меню доступны история, карта, списки поездов и станций.
            3. На интерактивной карте нажимайте на названия станций для быстрого перехода.
            4. В списках выбирайте объект для просмотра подробной информации.
            Для возврата используйте кнопку «Назад» вверху экрана или системную кнопку.
        """.trimIndent()
    }
}