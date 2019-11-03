package com.tatvum.realtimechat.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.UserListBinding

class UserFragment : Fragment() {


    private lateinit var binding: UserListBinding
    private lateinit var viewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_list, container, false
        )
        viewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getUsers()

        val adapter = UserAdapter()
        binding.userList.adapter = adapter

        viewModel.userItemList.observe(this, Observer { userItemList ->
            if (userItemList != null) {
                adapter.submitList(userItemList)
            }
//            else {
//                Snackbar.make(
//                    binding.parent,
//                    getString(R.string.valid_no_user_list),
//                    Snackbar.LENGTH_SHORT
//                ).show()
//            }
        })

        viewModel.getUsers()
        return binding.root
    }
}