package com.tatvum.realtimechat.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.tatvum.realtimechat.EMPTY
import com.tatvum.realtimechat.NO_USER
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.LoginBinding
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login, container, false
        )
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventValidationComplete.observe(this, Observer { errorType ->
            val message: String = getErrorMessage(errorType)

            if (errorType != 0) {
                if (errorType == EMPTY) {
                    Snackbar.make(binding.parent, message, Snackbar.LENGTH_SHORT).show()

                } else if (errorType == NO_USER) {
                    val snackBar = Snackbar.make(binding.parent, message, Snackbar.LENGTH_SHORT)
                    snackBar.setAction(getString(R.string.sign_up).toUpperCase(Locale.US)) {
                        signUp()
                    }
                    snackBar.show()
                }
                viewModel.validationComplete()
            }
        })

        viewModel.eventNavigateFromLogin.observe(this, Observer { moveFromLogin ->
            if (moveFromLogin) signIn()
        })

        viewModel.eventMoveToSignUp.observe(this, Observer { moveToSignUp ->
            if (moveToSignUp) signUp()
        })
        return binding.root
    }

    private fun getErrorMessage(errorType: Int?): String {
        return when (errorType) {
            EMPTY -> getString(R.string.valid_username)
            NO_USER -> getString(R.string.valid_user_not_found)
            else -> ""
        }
    }

    private fun signIn() {
        NavHostFragment.findNavController(this)
            .navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        viewModel.loginFinish()
    }

    private fun signUp() {
        NavHostFragment.findNavController(this)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        binding.userName.setText("")
    }
}