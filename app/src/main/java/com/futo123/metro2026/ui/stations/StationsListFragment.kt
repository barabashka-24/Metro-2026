package com.futo123.metro2026.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.futo123.metro2026.R
import com.futo123.metro2026.data.Repository
import com.futo123.metro2026.data.Station
import com.futo123.metro2026.databinding.FragmentStationsListBinding
import com.futo123.metro2026.databinding.ItemStationBinding

class StationsListFragment : Fragment() {
    private var _binding: FragmentStationsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = StationsAdapter(Repository.stations) { station ->
            // Передаём stationId через Bundle, используя action
            val bundle = Bundle().apply {
                putInt("stationId", station.id)
            }
            findNavController().navigate(R.id.action_stations_to_detail, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Адаптер
class StationsAdapter(
    private val stations: List<Station>,
    private val onItemClick: (Station) -> Unit
) : RecyclerView.Adapter<StationsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemStationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val station = stations[position]
        holder.binding.stationName.text = station.name
        holder.binding.stationDesc.text = station.shortDescription
        holder.binding.root.setOnClickListener { onItemClick(station) }
    }

    override fun getItemCount(): Int = stations.size
}