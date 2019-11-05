package com.tatvum.realtimechat.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ChatViewModelFactory(private val from: String, private val to: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(from, to) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}