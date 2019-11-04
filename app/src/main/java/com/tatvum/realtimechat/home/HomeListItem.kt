package com.tatvum.realtimechat.home

data class HomeListItem(
    val userName: String = "",
    val displayName: String = "",
    val imageUrl: String = "'",
    var messageLine: String = "",
    var time: String = ""
)