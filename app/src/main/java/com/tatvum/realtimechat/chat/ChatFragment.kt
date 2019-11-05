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
import androidx.recyclerview.widget.RecyclerView
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
//        binding.chat.smoothScrollToPosition(adapter.itemCount - 1)

        viewModel.chatList.observe(this, Observer { chatList ->
            if (chatList != null) {
                adapter.submitList(chatList)
                binding.chat.scrollToPosition(adapter.itemCount - 1)
            }
        })

        viewModel.eventSendMessage.observe(this, Observer { status ->
            if (status) {
                binding.chat.scrollToPosition(adapter.itemCount - 1)
                viewModel.messageSent()
            }
        })

        val linearLayoutManager = LinearLayoutManager(activity)
        binding.chat.layoutManager = linearLayoutManager

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
//            override fun onChanged() {
//                binding.chat.scrollToPosition(adapter.itemCount - 1)
//            }

            //            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
//                recycler_view_list.scrollToPosition(0)
//            }
//            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
//                recycler_view_list.scrollToPosition(0)
//            }
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.chat.scrollToPosition(adapter.itemCount - 1)
            }
//            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
//                recycler_view_list.scrollToPosition(0)
//            }
//            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
//                recycler_view_list.scrollToPosition(0)
//            }
        })

        return binding.root
    }
}