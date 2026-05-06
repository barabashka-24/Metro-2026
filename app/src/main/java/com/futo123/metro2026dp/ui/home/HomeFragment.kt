package com.futo123.metro2026dp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.futo123.metro2026dp.databinding.FragmentHomeBinding
import com.futo123.metro2026dp.R

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMenu.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_menu)
        }
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}