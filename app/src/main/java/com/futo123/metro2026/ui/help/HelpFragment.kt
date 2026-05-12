package com.futo123.metro2026.ui.help

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.futo123.metro2026.databinding.FragmentHelpBinding
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
            
            <p>Этот путеводитель познакомит вас с историей станций и электропоездов Петербургского метрополитена.</p>
            
            <h3>🏠 Главный экран</h3>
            <p>В верхнем левом углу кнопка <b>«Меню»</b>, В верхнем правом углу — <b>«Настройки»</b>.
            Нажмите <b>«Меню»</b>, чтобы перейти к выбору раздела.</p>
            
            <h3>📋 Меню</h3>
            <p>В меню предоставлен выбор разделов:</p>
            <ul>
                <li><b>  Метро СПб</b> — краткая история метрополитена.</li>
                <li><b>  Карта</b> — интерактивная схема линий, нажимайте на названия станций для подробностей.</li>
                <li><b>  Поезда</b> — список подвижного состава всех лет с историческими справками.</li>
                <li><b>  Станции</b> — все станции, сгруппированные по линиям метрополитена. В разделе представлены как открытые станции, так и станции на этапах стройки/разработки</li>
            </ul>
            
            <h3>🗺️ Карта</h3>
            <p>Карту можно <b>масштабировать</b> жестами (pinch‑to‑zoom). 
            Чтобы увидеть информацию о станции, коснитесь её названия на схеме.</p>
            
            <h3>🚇 Список станций</h3>
            <p>Станции разбиты по шести линиям. Нажмите на название линии, чтобы <b>раскрыть</b> список её станций. 
            Повторное нажатие свернёт список. Коснитесь любой станции, чтобы прочитать статью.</p>
            
            <h3>🚃 Электропоезда</h3>
            <p>Аналогично: выберите модель поезда из списка и узнайте интересные факты о ней.</p>
            
            <h3>🔙 Возврат назад</h3>
            <p>Используйте кнопку <b>«Назад»</b> в конце статьи или системную кнопку вашего устройства.</p>
            
            <h3>ℹ️ Источники</h3>
            <p>Информация взята с официального сайта Петербургского метрополитена:<br>
            <a href="http://www.metro.spb.ru">www.metro.spb.ru</a></p>
            <p>Иконки:<br>
            <a href="https://icons8.com/">icons8.com</a></p>
            
        """.trimIndent()

        val spanned: Spanned = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        binding.textHelp.text = spanned
        binding.textHelp.movementMethod = LinkMovementMethod.getInstance() // чтобы ссылки кликались
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}