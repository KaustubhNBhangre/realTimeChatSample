package com.tatvum.realtimechat.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.*
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.listeners.AddUser
import com.tatvum.realtimechat.model.user.listeners.CheckUser

class SignUpVieModel : ViewModel(), CheckUser, AddUser {
    val userName = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()

    private val userModel = UserModel()

    private lateinit var userNameText: String
    private lateinit var firstNameText: String
    private lateinit var lastNameText: String

    private val _eventValComplete = MutableLiveData<Int>()
    val eventValComplete: LiveData<Int>
        get() = _eventValComplete


    private val _eventNavToLogin = MutableLiveData<Boolean>()
    val eventNavToLogin: LiveData<Boolean>
        get() = _eventNavToLogin

    init {
        userName.value = ""
        firstName.value = ""
        lastName.value = ""
        _eventValComplete.value = 0
        _eventNavToLogin.value = false
    }

    //Validations
    fun validateSignUp() {

        userNameText = userName.value ?: ""
        firstNameText = firstName.value ?: ""
        lastNameText = lastName.value ?: ""

        when {
            firstNameText.trim() == "" -> validationError(EMPTY_FIRST_NAME)
            lastNameText.trim() == "" -> validationError(EMPTY_LAST_NAME)
            userNameText.trim() == "" -> validationError(EMPTY_USER)
            else -> userModel.checkUser(userNameText, this)
        }
    }

    private fun validationError(no: Int) {
        _eventValComplete.value = no
    }

    fun validationComplete() {
        _eventValComplete.value = 0
    }

    private fun navToLogin() {
        _eventNavToLogin.value = true
    }

    fun navLoginComplete() {
        _eventNavToLogin.value = false
    }


    override fun userFound(status: Boolean) {
        if (status) {
            validationError(EXIST_USER)
        } else {
            userModel.addUser(firstNameText.trim(), lastNameText.trim(), userNameText.trim(), this)
        }
    }

    override fun userAdded(status: Boolean) {
        if (status) {
            navToLogin()
        } else {
            validationError(USER_CREATION_FAILED)
        }

    }
}