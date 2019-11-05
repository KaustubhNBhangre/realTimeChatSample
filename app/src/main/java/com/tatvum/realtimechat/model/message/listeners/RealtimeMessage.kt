package com.tatvum.realtimechat.model.message.listeners

interface RealtimeMessage {
    fun messageUpdated(userUpdated: Boolean)
}