package com.tatvum.realtimechat.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tatvum.realtimechat.R
import com.tatvum.realtimechat.databinding.ChatBinding

class ChatFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        val binding = DataBindingUtil.inflate<ChatBinding>(
            inflater,
            R.layout.chat, container, false
        )
        return binding.root
    }
}