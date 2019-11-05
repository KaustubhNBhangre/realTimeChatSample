package com.tatvum.realtimechat.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatvum.realtimechat.databinding.HeaderChatItemBinding
import com.tatvum.realtimechat.databinding.LeftChatBubbleBinding
import com.tatvum.realtimechat.databinding.RightChatBubbleBinding

const val LEFT_BUBBLE = 1
const val RIGHT_BUBBLE = 2
const val HEADER_BUBBLE = 3

class ChatAdapter : ListAdapter<ChatLisItem, RecyclerView.ViewHolder>(ChatListDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is LeftChatViewHolder -> holder.bind(item)
            is RightChatViewHolder -> holder.bind(item)
            is HeaderChatViewHolder -> holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LEFT_BUBBLE -> LeftChatViewHolder.from(parent)
            RIGHT_BUBBLE -> RightChatViewHolder.from(parent)
            HEADER_BUBBLE -> HeaderChatViewHolder.from(parent)
            else -> throw ClassCastException("Unkown viewType $viewType")
        }
    }

    class ChatListDiffCallback : DiffUtil.ItemCallback<ChatLisItem>() {

        override fun areItemsTheSame(oldItem: ChatLisItem, newItem: ChatLisItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ChatLisItem, newItem: ChatLisItem): Boolean {
            return oldItem == newItem
        }
    }

    class LeftChatViewHolder private constructor(val binding: LeftChatBubbleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatLisItem) {
            binding.viewModel = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LeftChatViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LeftChatBubbleBinding.inflate(layoutInflater, parent, false)
                return LeftChatViewHolder(binding)
            }
        }
    }

    class RightChatViewHolder private constructor(val binding: RightChatBubbleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatLisItem) {
            binding.viewModel = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RightChatViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RightChatBubbleBinding.inflate(layoutInflater, parent, false)
                return RightChatViewHolder(binding)
            }
        }
    }

    class HeaderChatViewHolder private constructor(val binding: HeaderChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatLisItem) {
            binding.viewModel = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HeaderChatViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HeaderChatItemBinding.inflate(layoutInflater, parent, false)
                return HeaderChatViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }
}