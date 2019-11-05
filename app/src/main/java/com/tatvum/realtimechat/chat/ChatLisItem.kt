package com.tatvum.realtimechat.chat


import com.tatvum.realtimechat.model.message.Message
import com.tatvum.realtimechat.model.user.User

data class ChatLisItem(
    val id: Int = 0,
    val displayName: String = "",
    val viewType: Int = 0,
    val from: String = "",
    val to: String = "",
    val imageUrl: String = "'",
    var message: String = "",
    var time: String = ""
)

fun Message.convertFromMessage(from: String, id: Int, to: User): ChatLisItem {
    if (from == this.from) {
        return ChatLisItem(
            id,
            "You",
            RIGHT_BUBBLE,
            this.from,
            this.to ?: "",
            this.imageUrl ?: "",
            this.message ?: "",
            ""
        )
    } else {
        return ChatLisItem(
            id,
            to.firstName + " " + to.lastName,
            LEFT_BUBBLE,
            this.from ?: "",
            this.to ?: "",
            this.imageUrl ?: "",
            this.message ?: "",
            ""
        )
    }
}

