package com.tatvum.realtimechat.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.HomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
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
        viewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val userName = HomeFragmentArgs.fromBundle(arguments!!).userName

        val sharedPrefs =
            activity?.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        viewModel.sharedPrefs=sharedPrefs!!
        viewModel.saveUser(userName)
        viewModel.eventNavToChat.observe(this, Observer { navToChat ->
            if (navToChat) {
                chat()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }


    fun chat() {
        NavHostFragment.findNavController(this)
            .navigate(HomeFragmentDirections.actionHomeFragmentToUserFragment())
        viewModel.navChatComplete()
    }
}