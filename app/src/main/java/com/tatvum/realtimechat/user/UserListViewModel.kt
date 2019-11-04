package com.tatvum.realtimechat.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.model.user.User
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.listeners.GetAllUsers

class UserListViewModel : ViewModel(), GetAllUsers {
    private val userModel = UserModel()

    var currentUser: String=""
    private val _userItemList = MutableLiveData<MutableList<UserListItem>>()
    val userItemList: LiveData<MutableList<UserListItem>>
        get() = _userItemList


    init {
        _userItemList.value = null
    }


    fun getUsers() {
        userModel.getUserList(this)
    }

    override fun getUsers(userList: List<User>?) {
        if (userList != null) {
            val userItemList = mutableListOf<UserListItem>()
            for (user: User in userList) {
                if (currentUser != user.userName)
                    userItemList.add(user.convertFromUser())
            }
            _userItemList.value = userItemList
        }
    }
}