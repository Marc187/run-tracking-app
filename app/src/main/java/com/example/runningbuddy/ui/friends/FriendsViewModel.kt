package com.example.runningbuddy.ui.friends

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.runningbuddy.models.User
import com.example.runningbuddy.repositories.FriendRepository

class FriendsViewModel (val app: Application) : AndroidViewModel(app) {
    val friends = MutableLiveData<MutableList<User>>()

    private var friendRepository: FriendRepository = FriendRepository(app)
    var name = ""

    fun getUsersByName(){
        friendRepository.getUsersByName(friends, name)
    }
}