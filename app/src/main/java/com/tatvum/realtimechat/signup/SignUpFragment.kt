package com.tatvum.realtimechat.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tatvum.realtimechat.*
import com.tatvum.realtimechat.databinding.SignupBinding
import java.util.*


class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpVieModel
    private lateinit var binding: SignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.signup, container, false
        )
        viewModel = ViewModelProviders.of(this).get(SignUpVieModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventValComplete.observe(this, Observer { errorType ->
            if (errorType != 0) {
                Snackbar.make(
                    binding.signUpParent,
                    getErrorMessage(errorType),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.validationComplete()
            }
        })

        viewModel.eventNavToLogin.observe(this, Observer { navToLogin ->
            if (navToLogin) {
                Snackbar.make(
                    binding.signUpParent,
                    getString(R.string.user_created),
                    Snackbar.LENGTH_SHORT
                )
                    .setAction(getString(R.string.login).toUpperCase(Locale.US)) {
                        logIn()
                    }.show()
            }

        })

        return binding.root
    }

    private fun getErrorMessage(errorType: Int?): String {
        return when (errorType) {
            EMPTY_USER -> getString(R.string.valid_username)
            EMPTY_FIRST_NAME -> getString(R.string.valid_first_name)
            EMPTY_LAST_NAME -> getString(R.string.valid_last_name)
            EXIST_USER -> getString(R.string.valid_user_exist)
            USER_CREATION_FAILED -> getString(R.string.valid_user_creation)
            else -> ""
        }
    }

    private fun logIn() {
        NavHostFragment.findNavController(this).navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())

        viewModel.navLoginComplete()
    }
}