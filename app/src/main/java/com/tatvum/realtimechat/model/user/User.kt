package com.tatvum.realtimechat.model.user

data class User(
    val userName: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val imageUrl: String? = null,
    val users: List<String>? = null
)