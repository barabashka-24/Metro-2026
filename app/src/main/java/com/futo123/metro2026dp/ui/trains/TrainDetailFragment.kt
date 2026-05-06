package com.futo123.metro2026dp.ui.trains

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.futo123.metro2026dp.data.Repository
import com.futo123.metro2026dp.databinding.FragmentTrainDetailBinding

class TrainDetailFragment : Fragment() {
    private var _binding: FragmentTrainDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем trainId из аргументов
        val trainId = arguments?.getInt("trainId") ?: -1
        val train = Repository.getTrainById(trainId)

        if (train != null) {
            binding.trainImage.setImageResource(train.imageResId)
            binding.trainName.text = train.name
            binding.trainHistory.text = train.history   // см. поле data class Train (history, а не fullHistory)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}