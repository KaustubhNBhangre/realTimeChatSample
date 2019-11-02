package com.tatvum.realtimechat.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatvum.realtimechat.EMPTY
import timber.log.Timber

class LoginViewModel : ViewModel() {

     val userName = MutableLiveData<String>()
//    val userName: LiveData<String>
//        get() = _userName

    private val _eventLoginFinish = MutableLiveData<Boolean>()
    val eventLoginFinish: LiveData<Boolean>
        get() = _eventLoginFinish

    private val _eventValidationComplete = MutableLiveData<Int>()
    val eventValidationComplete: LiveData<Int>
        get() = _eventValidationComplete

    init {
        userName.value = ""
        _eventValidationComplete.value = 0
        _eventLoginFinish.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("LoginViewModel Cleared")
    }

    fun loginFinish() {
        _eventLoginFinish.value = false;
    }

    fun finishLogin() {
        _eventLoginFinish.value = true;
    }

    private fun validationError(no: Int) {
        _eventValidationComplete.value = no
    }

    fun validationComplete() {
        _eventValidationComplete.value = 0
    }


    fun validateLogin() {
        Timber.i("---This is  text:" + userName.value)
        if (userName.value.equals("")) {
            validationError(EMPTY);
        }

    }
}