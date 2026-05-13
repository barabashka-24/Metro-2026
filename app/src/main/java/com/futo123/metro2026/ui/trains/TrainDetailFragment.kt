package com.futo123.metro2026.ui.trains

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.futo123.metro2026.MyApplication
import com.futo123.metro2026.databinding.FragmentTrainDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        val trainId = arguments?.getInt("trainId") ?: return
        val app = requireActivity().application as MyApplication
        val repository = app.trainRepository

        lifecycleScope.launch(Dispatchers.IO) {
            val train = repository.getTrainById(trainId)
            launch(Dispatchers.Main) {
                if (train != null) {
                    binding.trainImage.setImageResource(train.imageResId)
                    binding.trainName.text = train.name
                    binding.trainHistory.text = Html.fromHtml(train.history, Html.FROM_HTML_MODE_LEGACY)
                }
            }
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