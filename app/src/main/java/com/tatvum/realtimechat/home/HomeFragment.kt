package com.tatvum.realtimechat.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.tatvum.realtimechat.PREF_NAME
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.HomeBinding
import com.tatvum.realtimechat.hideKeyboard

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var binding: HomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home, container, false
        )

        val sPrefs = activity?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val currentUser = sPrefs!!.getString(PREF_NAME, "") ?: ""

        viewModelFactory = HomeViewModelFactory(currentUser)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = HomeAdapter(HomeItemListener { userName ->
            chat(userName)
        })





        viewModel.eventNavToUserList.observe(this, Observer { navToUserList ->
            if (navToUserList) {
                userList()
            }
        })

        binding.chatUserList.adapter = adapter
        viewModel.homeItemList.observe(this, Observer { homeItemList ->
            if (homeItemList != null) {
                adapter.submitList(homeItemList)
            }
        })


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    private fun chat(userName: String) {
        NavHostFragment.findNavController(this)
            .navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment(userName))
        this.hideKeyboard()
    }

    private fun userList() {
        NavHostFragment.findNavController(this)
            .navigate(HomeFragmentDirections.actionHomeFragmentToUserFragment())
        viewModel.navUserListComplete()
        this.hideKeyboard()
    }
}