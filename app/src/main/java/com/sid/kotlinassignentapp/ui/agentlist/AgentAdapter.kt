package com.sid.kotlinassignentapp.ui.agentlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sid.kotlinassignentapp.databinding.ItemAgentBinding

class AgentPagingAdapter(
    private val onItemClick: (AgentUiModel) -> Unit
) : PagingDataAdapter<AgentUiModel, AgentPagingAdapter.AgentViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val binding = ItemAgentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AgentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class AgentViewHolder(
        private val binding: ItemAgentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AgentUiModel) {
            binding.tvName.text = item.name
            // Avatar loading (Coil/Glide) can be added later

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<AgentUiModel>() {
            override fun areItemsTheSame(
                oldItem: AgentUiModel,
                newItem: AgentUiModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: AgentUiModel,
                newItem: AgentUiModel
            ): Boolean = oldItem == newItem
        }
    }
}
