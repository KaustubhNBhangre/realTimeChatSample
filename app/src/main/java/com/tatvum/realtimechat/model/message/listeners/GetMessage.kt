package com.tatvum.realtimechat.model.message.listeners

import com.tatvum.realtimechat.model.message.Message

interface GetMessage {
    fun getMessage(message: Message?)
}