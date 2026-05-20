package com.barabashka_24.metro2026.ui.help

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.barabashka_24.metro2026.databinding.FragmentHelpBinding
import android.text.Html
import android.text.Spanned

class HelpFragment : Fragment() {
    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val htmlText = """
            <h2 style="text-align: center;">📖 Справка</h2>
            
            <p>&emsp;Этот путеводитель познакомит вас с историей станций и электропоездов Петербургского метрополитена.</p>
            
            <h3>🏠 Главный экран</h3>
            <p>&emsp;В верхнем левом углу кнопка <b>«Меню»</b>, В верхнем правом углу — <b>«Настройки»</b>.
            Нажмите <b>«Меню»</b>, чтобы перейти к выбору раздела.</p>
            
            <h3>📋 Меню</h3>
            <p>В меню предоставлен выбор разделов:</p>
            <ul>
                <li><b>&emsp;Метро СПб</b> — краткая история метрополитена.</li>
                <li><b>&emsp;Карта</b> — интерактивная схема линий, нажимайте на названия станций для подробностей.</li>
                <li><b>&emsp;Поезда</b> — список подвижного состава всех лет с историческими справками.</li>
                <li><b>&emsp;Станции</b> — все станции, сгруппированные по линиям метрополитена. В разделе представлены как открытые станции, так и станции на этапах стройки/разработки</li>
            </ul>
            
            <h3>🗺️ Карта</h3>
            <p>&emsp;Карту можно <b>масштабировать</b> жестами (pinch‑to‑zoom). 
            Чтобы увидеть информацию о станции, коснитесь её названия на схеме.</p>
            
            <h3>🚇 Список станций</h3>
            <p>&emsp;Станции разбиты по шести линиям. Нажмите на название линии, чтобы <b>раскрыть</b> список её станций. 
            Повторное нажатие свернёт список. Коснитесь любой станции, чтобы прочитать статью.</p>
            
            <h3>🚃 Электропоезда</h3>
            <p>&emsp;Аналогично: выберите модель поезда из списка и узнайте интересные факты о ней.</p>
            
            <h3>🔙 Возврат назад</h3>
            <p>&emsp;Используйте кнопку <b>«Назад»</b> в конце статьи или системную кнопку вашего устройства.</p>
            
            <h3>ℹ️ Источники</h3>
            <p>Информация для статей:<br>
            <a href="http://www.metro.spb.ru">www.metro.spb.ru</a><br>
            <a href="https://metro.vpeterburge.ru/">metro.vpeterburge.ru</a><br>
            <a href="https://ru.wikipedia.org/">ru.wikipedia.org</a></p>
            <p>Схема метро для интерактивной карты:<br>
            <a href="https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9F%D0%B5%D1%82%D0%B5%D1%80%D0%B1%D1%83%D1%80%D0%B3%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0">Alex 'Florstein' Fedorov via wikipedia CC BY-SA 4.0</a></p>
            <p>Иконки:<br>
            <a href="https://icons8.com/">icons8.com</a><br>
            <a href="https://www.flaticon.com/">www.flaticon.com</a></p>
            
        """.trimIndent()

        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val fontSize = prefs.getFloat("pref_font_size", 16f)
        binding.textHelp.textSize = fontSize

        val spanned: Spanned = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        binding.textHelp.text = spanned
        binding.textHelp.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}