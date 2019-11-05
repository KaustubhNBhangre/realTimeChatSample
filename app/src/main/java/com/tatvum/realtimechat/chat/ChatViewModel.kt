package com.tatvum.realtimechat.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.model.message.Message
import com.tatvum.realtimechat.model.message.MessageModel
import com.tatvum.realtimechat.model.message.listeners.AddMessage
import com.tatvum.realtimechat.model.message.listeners.GetAllMessages
import com.tatvum.realtimechat.model.user.User
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.listeners.GetUser
import com.tatvum.realtimechat.model.user.listeners.UpdateUser

class ChatViewModel(private val fromValue: String, private val toValue: String) : ViewModel(),
    AddMessage,
    GetAllMessages, UpdateUser, GetUser {

    private val userModel = UserModel()
    private val messageModel = MessageModel()

    private var flag = true
    private var userUpdatedflag = false
    private var toObj: User? = null

    private val _chatList = MutableLiveData<MutableList<ChatLisItem>>()
    val chatList: LiveData<MutableList<ChatLisItem>>
        get() = _chatList

    val message = MutableLiveData<String>()

    private val _eventSendMessage = MutableLiveData<Boolean>()
    val eventSendMessage: LiveData<Boolean>
        get() = _eventSendMessage

    init {
        _chatList.value = null
        _eventSendMessage.value = false
        getAllChats()
    }

    private fun sendMessageEvent() {
        _eventSendMessage.value = true
    }

    fun messageSent() {
        _eventSendMessage.value = false
    }

    fun sendMessage() {
        val msg = message.value ?: ""
        if (msg != "") {
            messageModel.addMessage(fromValue, toValue, msg, this)
            message.value = ""
        }
    }

    override fun messageAdded(status: Boolean) {
        if (userUpdatedflag) {
            sendMessageEvent()
        } else {
            userModel.updateUser(fromValue, toValue, this)
        }
    }

    override fun userUpdated(status: Boolean) {
        if (status) {
            if (flag) {
                flag = false
                userModel.updateUser(toValue, fromValue, this)
            }else{
                sendMessageEvent()
                userUpdatedflag = true
            }
        }
    }

    fun getAllChats() {
        userModel.getUser(toValue, this)
    }

    override fun getUser(user: User?, id: String) {
        toObj = user
        messageModel.getMessageRealtime(fromValue, toValue, this)
    }

    override fun getMessages(messageList: List<Message>?) {
        if (messageList != null) {
            val chats = mutableListOf<ChatLisItem>()
            for (i: Int in messageList.indices) {
                chats.add(messageList[i].convertFromMessage(fromValue, i, toObj!!))
            }
            _chatList.value = chats
        }
    }
}