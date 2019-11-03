package com.tatvum.realtimechat.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.EMPTY
import com.tatvum.realtimechat.NO_USER
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.interfaces.CheckUserListener

class LoginViewModel : ViewModel(), CheckUserListener {

    val userName = MutableLiveData<String>()
    private val userModel = UserModel()

    private val _eventNavigateFromLogin = MutableLiveData<Boolean>()
    val eventNavigateFromLogin: LiveData<Boolean>
        get() = _eventNavigateFromLogin

    private val _eventValidationComplete = MutableLiveData<Int>()
    val eventValidationComplete: LiveData<Int>
        get() = _eventValidationComplete

    private val _eventMoveToSignUp = MutableLiveData<Boolean>()
    val eventMoveToSignUp: LiveData<Boolean>
        get() = _eventMoveToSignUp


    init {
        userName.value = ""
        _eventValidationComplete.value = 0
        _eventNavigateFromLogin.value = false
        _eventMoveToSignUp.value = false
    }

    fun loginFinish() {
        _eventNavigateFromLogin.value = false
    }

    private fun navigateFromLogin() {
        _eventNavigateFromLogin.value = true
    }

    private fun validationError(no: Int) {
        _eventValidationComplete.value = no
    }

    fun validationComplete() {
        _eventValidationComplete.value = 0
    }


    fun validateLogin() {
        val enteredText = userName.value
        if (enteredText != null) {
            if (enteredText == "") {
                validationError(EMPTY)
            } else {
                userModel.checkUser(enteredText, this)
            }
        }
    }

    override fun userFound(status: Boolean) {
        if (!status) {
            validationError(NO_USER)
        } else {
            validationComplete()
            navigateFromLogin()
        }
    }

    fun navigateToSignUp() {
        _eventMoveToSignUp.value = true
    }

}