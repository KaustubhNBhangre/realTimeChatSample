package com.tatvum.realtimechat.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _eventNavToChat = MutableLiveData<Boolean>()
    val eventNavToChat: LiveData<Boolean>
        get() = _eventNavToChat


    init {
        _eventNavToChat.value = false
    }

    fun navToChat() {
        _eventNavToChat.value = true
    }

    fun navChatComplete() {
        _eventNavToChat.value = false
    }

}