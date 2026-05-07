package com.futo123.metro2026.ui.trains

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
import com.futo123.metro2026.data.Train
import com.futo123.metro2026.databinding.FragmentTrainsListBinding
import com.futo123.metro2026.databinding.ItemTrainBinding

class TrainsListFragment : Fragment() {
    private var _binding: FragmentTrainsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = TrainsAdapter(Repository.trains) { train ->
            val bundle = Bundle().apply {
                putInt("trainId", train.id)
            }
            findNavController().navigate(R.id.action_trains_to_detail, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class TrainsAdapter(
    private val trains: List<Train>,
    private val onItemClick: (Train) -> Unit
) : RecyclerView.Adapter<TrainsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTrainBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val train = trains[position]
        holder.binding.trainName.text = train.name
        holder.binding.trainDesc.text = train.description   // в модели поле description
        holder.binding.root.setOnClickListener { onItemClick(train) }
    }

    override fun getItemCount(): Int = trains.size
}