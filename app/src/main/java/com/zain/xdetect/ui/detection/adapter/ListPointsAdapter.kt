package com.zain.xdetect.ui.detection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zain.xdetect.databinding.ItemPointsBinding

class ListPointsAdapter(private val listPoints: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemPointsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PointsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PointsViewHolder -> holder.bind(listPoints[position])
        }
    }

    override fun getItemCount(): Int = listPoints.size

    inner class PointsViewHolder(private val binding: ItemPointsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(point: String) {
            binding.tvPoints.text = point
        }
    }
}