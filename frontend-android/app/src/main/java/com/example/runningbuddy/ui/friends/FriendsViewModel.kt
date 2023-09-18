package com.example.runningbuddy.ui.friends

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.models.User
import com.example.runningbuddy.repositories.UsersFriendRepository
import com.example.runningbuddy.repositories.SubscribeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendsViewModel (val app: Application) : AndroidViewModel(app) {
    val friends = MutableLiveData<MutableList<User>>()

    private var usersFriendRepository: UsersFriendRepository = UsersFriendRepository(app)
    private var subscribeRepository: SubscribeRepository = SubscribeRepository(app)
    var name = ""

    fun getUsersByName(){
        if (name != "") {
            usersFriendRepository.getUsersByName(friends, MainActivity.userId, name)
        }
    }

    fun updateSubscribe(indexUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = friends.value?.get(indexUser)

            if (user!!.subscribed) {
                subscribeRepository.deleteSubscribe(user.id)
                user.subscribed = false
            } else {
                subscribeRepository.addSubscribe(user.id)
                user.subscribed = true
            }
            friends.value?.get(indexUser)?.let { println(it.subscribed) }
        }
    }
}