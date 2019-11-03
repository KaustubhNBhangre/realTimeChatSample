package com.tatvum.realtimechat.model.message

import com.google.firebase.Timestamp

data class Message(
    val from: String? = null,
    val to: String? = null,
    val message: String? = null,
    val imageUrl: String? = null,
    val timeStamp: Timestamp? = null
)