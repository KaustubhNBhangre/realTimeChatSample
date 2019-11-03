package com.tatvum.realtimechat.home

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
        
        viewModel.eventNavToChat.observe(this, Observer { navToChat ->
            if (navToChat) {
                chat()
            }
        })

//        binding.chatUserList.setOnClickListener { view: View ->
//            view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserFragment())
//            val snackBar: Snackbar = Snackbar.make(binding.parent, "Message", Snackbar.LENGTH_SHORT)
//            snackBar.show();
//        }
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