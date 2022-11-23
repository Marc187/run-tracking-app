package com.example.runningbuddy.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runningbuddy.R
import com.example.runningbuddy.adapters.FriendRecyclerViewAdapter


class FriendsFragment : Fragment() {
    private lateinit var friendsViewModel: FriendsViewModel
    private lateinit var rvFriends: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.search_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.title = "Recherche amis"

        // Viewmodel
        this.friendsViewModel = ViewModelProvider(this)[FriendsViewModel::class.java]


        requireView().findViewById<EditText>(R.id.editTextFriendSearch).setText(friendsViewModel.name)
        requireView().findViewById<EditText>(R.id.editTextFriendSearch).addTextChangedListener {
            friendsViewModel.name = it.toString()
            friendsViewModel.getUsersByName()
        }


        // RecyclerView des courses
        this.rvFriends = requireView().findViewById(R.id.rvFriends)
        this.rvFriends.layoutManager = LinearLayoutManager(context)

        this.friendsViewModel.friends.observe(viewLifecycleOwner) {
            this.rvFriends.adapter = FriendRecyclerViewAdapter(it, this.friendsViewModel)
        }
    }
}