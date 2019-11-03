package com.tatvum.realtimechat.model.user.interfaces

import com.tatvum.realtimechat.model.user.User

interface AddUserListener {
    fun userAdded(user: User)
}