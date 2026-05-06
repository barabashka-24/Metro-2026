package com.futo123.metro2026dp.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.futo123.metro2026dp.databinding.FragmentMenuBinding
import com.futo123.metro2026dp.R

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGeneral.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_general)
        }
        binding.btnMap.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_map)
        }
        binding.btnTrains.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_trains_list)
        }
        binding.btnStations.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_stations_list)
        }
        binding.btnAbout.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_about)
        }
        binding.btnHelp.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_help)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}