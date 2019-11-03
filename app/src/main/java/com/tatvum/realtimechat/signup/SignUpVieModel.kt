package com.tatvum.realtimechat.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.*
import com.tatvum.realtimechat.model.user.User
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.interfaces.AddUserListener
import com.tatvum.realtimechat.model.user.interfaces.CheckUserListener

class SignUpVieModel : ViewModel(), CheckUserListener, AddUserListener {
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

        if (firstNameText.trim() == "") {
            validationError(EMPTY_FIRST_NAME)
        } else if (lastNameText.trim().equals("")) {
            validationError(EMPTY_LAST_NAME)
        } else if (userNameText.trim() == "") {
            validationError(EMPTY_USER)
        } else {
            userModel.checkUser(userNameText, this)
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
        if(status){
            navToLogin()
        }else{
            validationError(USER_CREATION_FAILED)
        }

    }
}