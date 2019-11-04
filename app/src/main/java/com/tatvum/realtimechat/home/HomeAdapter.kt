package com.tatvum.realtimechat.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatvum.realtimechat.databinding.ChatItemBinding

class HomeAdapter(val clickListener: HomeItemListener) :
    ListAdapter<HomeListItem, HomeAdapter.ViewHolder>(UserDiffCallback()) {

    class UserDiffCallback : DiffUtil.ItemCallback<HomeListItem>() {
        override fun areContentsTheSame(oldItem: HomeListItem, newItem: HomeListItem): Boolean {
            return oldItem.userName == newItem.userName
        }

        override fun areItemsTheSame(oldItem: HomeListItem, newItem: HomeListItem): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder private constructor(val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: HomeListItem,
            clickListener: HomeItemListener
        ) {
            binding.home = item
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChatItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class HomeItemListener(val clickListener: (userName: String) -> Unit) {
    fun onClick(HomeListItem: HomeListItem) = clickListener(HomeListItem.userName)
}