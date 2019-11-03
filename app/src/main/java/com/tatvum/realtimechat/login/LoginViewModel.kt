package com.tatvum.realtimechat.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.EMPTY_USER
import com.tatvum.realtimechat.NO_USER
import com.tatvum.realtimechat.model.user.UserModel
import com.tatvum.realtimechat.model.user.listeners.CheckUser

class LoginViewModel : ViewModel(), CheckUser {

    val userName = MutableLiveData<String>()
    private val userModel = UserModel()

    private val _eventNavToHome = MutableLiveData<Boolean>()
    val eventNavToHome: LiveData<Boolean>
        get() = _eventNavToHome

    private val _eventValComplete = MutableLiveData<Int>()
    val eventValComplete: LiveData<Int>
        get() = _eventValComplete

    private val _eventNavToSignUp = MutableLiveData<Boolean>()
    val eventNavToSignUp: LiveData<Boolean>
        get() = _eventNavToSignUp


    init {
        userName.value = ""
        _eventValComplete.value = 0
        _eventNavToHome.value = false
        _eventNavToSignUp.value = false
    }

    fun loginFinish() {
        _eventNavToHome.value = false
    }

    private fun navTtoHome() {
        _eventNavToHome.value = true
    }


    //Validations
    fun validateLogin() {
        val enteredText = userName.value ?: ""
        if (enteredText.trim() == "") {
            validationError(EMPTY_USER)
        } else {
            userModel.checkUser(enteredText, this)
        }
    }

    private fun validationError(no: Int) {
        _eventValComplete.value = no
    }

    fun validationComplete() {
        _eventValComplete.value = 0
    }

    override fun userFound(status: Boolean) {
        if (!status) {
            validationError(NO_USER)
        } else {
            validationComplete()
            navTtoHome()
        }
    }

    fun navToSignUp() {
        _eventNavToSignUp.value = true
    }

    fun navSignUpComplete() {
        _eventNavToSignUp.value = false
    }

}