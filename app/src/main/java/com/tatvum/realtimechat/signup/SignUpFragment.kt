package com.tatvum.realtimechat.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.SignupBinding

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<SignupBinding>(
            inflater,
            R.layout.signup, container, false
        )
        return binding.root
    }
}