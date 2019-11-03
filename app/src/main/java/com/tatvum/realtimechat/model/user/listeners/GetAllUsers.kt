package com.tatvum.realtimechat.model.user.listeners

import com.tatvum.realtimechat.model.user.User

interface GetAllUsers {
    fun getUsers(userList: List<User>?)
}