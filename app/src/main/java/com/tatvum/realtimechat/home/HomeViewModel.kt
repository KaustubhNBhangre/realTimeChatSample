package com.tatvum.realtimechat.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.model.user.User
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.listeners.GetUser

class HomeViewModel : ViewModel(), GetUser {
    private val userModel = UserModel()
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

    fun saveUser(userName: String) {
        userModel.getUser(userName, this)
    }


    override fun getUser(user: User?) {
        if (user != null) {
            //TODO:Make list with users
        }
    }
}