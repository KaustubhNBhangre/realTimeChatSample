package com.tatvum.realtimechat.user

import com.tatvum.realtimechat.model.user.User

data class UserListItem(
    val userName: String = "",
    val displayName: String = "",
    val imageUrl: String = "'"
)


fun User.convertFromUser(): UserListItem {
    return UserListItem(
        this.userName ?: "",
        this.firstName + " " + this.lastName,
        this.imageUrl ?: ""
    )
}
