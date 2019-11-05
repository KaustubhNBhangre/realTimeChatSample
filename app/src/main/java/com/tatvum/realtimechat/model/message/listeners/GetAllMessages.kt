package com.tatvum.realtimechat.model.message.listeners

import com.tatvum.realtimechat.model.message.Message

interface GetAllMessages {
    fun getMessages(messageList: List<Message>?)
}