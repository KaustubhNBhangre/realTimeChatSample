package com.tatvum.realtimechat.user

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.tatvum.realtimechat.PREF_NAME
import com.tatvum.realtimechat.model.user.User
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.listeners.GetAllUsers

class UserListViewModel : ViewModel(), GetAllUsers {
    private val userModel = UserModel()
    lateinit var sharedPrefs: SharedPreferences
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
            val userObjectString = sharedPrefs.getString(PREF_NAME, "")
            val currentUser = Gson().fromJson(userObjectString, User::class.java)
            for (user: User in userList) {
                if (currentUser.userName != user.userName)
                    userItemList.add(user.convertFromUser())
            }
            _userItemList.value = userItemList
        }
    }
}