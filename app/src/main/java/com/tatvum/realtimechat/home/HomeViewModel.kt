package com.tatvum.realtimechat.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.tatvum.realtimechat.PREF_NAME
import com.tatvum.realtimechat.model.user.User
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.listeners.GetUser

class HomeViewModel : ViewModel(), GetUser {
    private val userModel = UserModel()
    lateinit var sharedPrefs: SharedPreferences
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
            val jsonString = Gson().toJson(user)
            with(sharedPrefs.edit()) {
                putString(PREF_NAME, jsonString)
                commit()
            }
        }
    }
}