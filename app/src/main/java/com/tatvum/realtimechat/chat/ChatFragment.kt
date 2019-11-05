package com.tatvum.realtimechat.chat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatvum.realtimechat.PREF_NAME
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.ChatBinding

class ChatFragment : Fragment() {


    private lateinit var binding: ChatBinding
    private lateinit var viewModel: ChatViewModel
    private lateinit var viewModelFactory: ChatViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.chat, container, false
        )

        val sPrefs = activity?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val fromValue = sPrefs!!.getString(PREF_NAME, "") ?: ""
        val toValue = ChatFragmentArgs.fromBundle(arguments!!).userName
        val adapter = ChatAdapter()

        viewModelFactory = ChatViewModelFactory(fromValue, toValue)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.chat.adapter = adapter

        viewModel.chatList.observe(this, Observer { chatList ->
            if (chatList != null) {
                adapter.submitList(chatList)
                binding.chat.scrollToPosition(adapter.itemCount - 1)
            }
        })

        viewModel.eventSendMessage.observe(this, Observer { status ->
            if (status) {
                viewModel.getAllChats()
                viewModel.messageSent()
            }
        })

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.stackFromEnd = true;
        binding.chat.layoutManager = linearLayoutManager;

        return binding.root
    }
}