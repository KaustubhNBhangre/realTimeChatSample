package com.tatvum.realtimechat.chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tatvum.realtimechat.PREF_NAME
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.ChatBinding

class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: ChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.chat, container, false
        )

        viewModel =
            ViewModelProviders.of(this).get(ChatViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val sPrefs = activity?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val currentUser = sPrefs!!.getString(PREF_NAME, "") ?: ""
        viewModel.fromValue = currentUser

        val toValue = ChatFragmentArgs.fromBundle(arguments!!).userName
        viewModel.toValue = toValue



        return binding.root
    }
}