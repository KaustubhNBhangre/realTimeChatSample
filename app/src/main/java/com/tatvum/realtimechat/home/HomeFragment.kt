package com.tatvum.realtimechat.home

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.HomeBinding
import com.tatvum.realtimechat.login.LoginViewModel

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<HomeBinding>(
            inflater,
            R.layout.home, container, false
        )
        val loginViewModel: LoginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.lifecycleOwner = this
//        binding.viewModel = loginViewModel

        binding.users.setOnClickListener { view: View ->
            val snackBar: Snackbar = Snackbar.make(binding.parent, "Message", Snackbar.LENGTH_SHORT)
            snackBar.show();
            //view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment())
        }

        binding.chatUserList.setOnClickListener { view: View ->
            //view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserFragment())
            //val snackBar: Snackbar = Snackbar.make(binding.parent, "Message", Snackbar.LENGTH_SHORT)
            //snackBar.show();
        }
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
}