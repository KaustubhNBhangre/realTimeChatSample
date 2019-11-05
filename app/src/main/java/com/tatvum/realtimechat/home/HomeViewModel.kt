package com.tatvum.realtimechat.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.model.message.Message
import com.tatvum.realtimechat.model.message.MessageModel
import com.tatvum.realtimechat.model.message.listeners.GetMessage
import com.tatvum.realtimechat.model.user.User
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.listeners.GetUser

class HomeViewModel(private val userName: String) : ViewModel(), GetUser {
    private val userModel = UserModel()
    private val messageModel = MessageModel()

    private lateinit var toObj:User;


    private val _eventNavToUserList = MutableLiveData<Boolean>()
    val eventNavToUserList: LiveData<Boolean>
        get() = _eventNavToUserList

    private val _homeItemList = MutableLiveData<MutableList<HomeListItem>>()
    val homeItemList: LiveData<MutableList<HomeListItem>>
        get() = _homeItemList

    private var homeItems = mutableListOf<HomeListItem>()

    init {
        _homeItemList.value = null
        _eventNavToUserList.value = false
        updateList()
    }


    fun navToUserList() {
        _eventNavToUserList.value = true
    }

    fun navUserListComplete() {
        _eventNavToUserList.value = false
    }

    private fun updateList() {
        userModel.getUser(userName, this)
    }

    override fun getUser(user: User?, id: String) {
        if (user != null) {
            if (user.users != null) {
                for (i in user.users.indices) {
                    if (i == user.users.size - 1) {
                        userModel.getUser(user.users[i], AddUserDetails(user.userName!!, true))
                    } else {
                        userModel.getUser(user.users[i], AddUserDetails(user.userName!!, false))
                    }

                }
            }
        }
    }


    inner class AddUserDetails(private val from: String, private val lastFlag: Boolean) : GetUser {
        override fun getUser(user: User?, id: String) {
            homeItems.add(
                HomeListItem(
                    user?.userName ?: "",
                    user?.firstName + " " + user?.lastName,
                    user?.imageUrl ?: ""
                )
            )
            if (lastFlag) {
                for (i in homeItems.indices) {
                    if (i == homeItems.size - 1) {
                        messageModel.getLastMessage(
                            from,
                            homeItems[i].userName,
                            CompleteHomeListItem(i, true, from)
                        )
                    } else {
                        messageModel.getLastMessage(
                            from,
                            homeItems[i].userName,
                            CompleteHomeListItem(i, false, from)
                        )
                    }
                }
            }
        }
    }

    inner class CompleteHomeListItem(
        private val index: Int,
        private val lastFlag: Boolean,
        private val from: String
    ) :
        GetMessage {
        override fun getMessage(message: Message?) {
            if (message != null) {
                var msgLine = ""
                msgLine += if (message.from == from) {
                    "You: "
                } else {
                    val tempArray=homeItems[index].displayName.split(" ")
                    tempArray[0] + ": "
                }

                msgLine += message.message
//                val date = message.timeStamp?.toDate()
                homeItems[index].messageLine = msgLine
                if (lastFlag) {
                    _homeItemList.value = homeItems
                }
            }
        }
    }




}