package com.tatvum.realtimechat.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.model.message.Message
import com.tatvum.realtimechat.model.message.MessageModel
import com.tatvum.realtimechat.model.message.listeners.AddMessage
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.listeners.UpdateUser

class ChatViewModel : ViewModel(), AddMessage, UpdateUser {

    private val userModel = UserModel()
    private val messageModel = MessageModel()

    lateinit var toValue: String
    lateinit var fromValue: String

    private val _chatList = MutableLiveData<MutableList<Message>>()
    val chatList: LiveData<MutableList<Message>>
        get() = _chatList

    val message = MutableLiveData<String>()

    private val _eventSendMessage = MutableLiveData<Boolean>()
    val eventSendMessage: LiveData<Boolean>
        get() = _eventSendMessage

    init {
        _chatList.value = null
        _eventSendMessage.value = false
    }

    private fun sendMessageEvent() {
        _eventSendMessage.value = true
    }

    fun sendMessage() {
        sendMessageEvent()
        val msg = message.value ?: ""
        if (msg != "") {
            messageModel.addMessage(fromValue, toValue, msg, this)
        }
    }

    private fun messageSent() {
        _eventSendMessage.value = false
    }

    private var flag = true
    override fun messageAdded(status: Boolean) {
        userModel.updateUser(fromValue, toValue, this)
    }

    override fun userUpdated(status: Boolean) {
        if (flag) {
            flag = false
            userModel.updateUser(toValue, fromValue, this)
            messageSent()
        }
    }
}