package com.tatvum.realtimechat.model.user.listeners

import com.tatvum.realtimechat.model.user.User

interface GetUser {
    fun getUser(user: User?, id: String)
}