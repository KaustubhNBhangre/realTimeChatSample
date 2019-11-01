package com.tatvum.realtimechat.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.UserListBinding

class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<UserListBinding>(
            inflater,
            R.layout.user_list, container, false
        )
        return binding.root
    }
}