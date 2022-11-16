package com.example.runningbuddy.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.runningbuddy.R
import com.example.runningbuddy.ui.settings.SettingViewModel

class FriendsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.search_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val friendsViewModel =
            ViewModelProvider(this).get(FriendsViewModel::class.java)
        requireActivity().actionBar?.title = "Recherche amis"
    }
}