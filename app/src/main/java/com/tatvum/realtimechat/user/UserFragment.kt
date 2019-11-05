package com.tatvum.realtimechat.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.tatvum.realtimechat.PREF_NAME
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.UserListBinding
import com.tatvum.realtimechat.hideKeyboard

class UserFragment : Fragment() {

    private lateinit var binding: UserListBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var viewModelFactory: UserViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_list, container, false
        )

        val sharedPrefs = activity?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val currentUser = sharedPrefs!!.getString(PREF_NAME, "") ?: ""

        viewModelFactory = UserViewModelFactory(currentUser)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = UserAdapter(UserItemListener { userName ->
            chat(userName)
        })

        binding.userList.adapter = adapter
        viewModel.userItemList.observe(this, Observer { userItemList ->
            if (userItemList != null) {
                adapter.submitList(userItemList)
            }
        })

        viewModel.getUsers()
        return binding.root
    }

    private fun chat(userName: String) {
        NavHostFragment.findNavController(this)
            .navigate(UserFragmentDirections.actionUserFragmentToChatFragment(userName))
        this.hideKeyboard()
    }
}