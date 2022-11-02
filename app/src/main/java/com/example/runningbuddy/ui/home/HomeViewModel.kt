package com.example.runningbuddy.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.runningbuddy.models.Course
import com.example.runningbuddy.repositories.UserActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val app: Application, val idUser: Int) : AndroidViewModel(app) {
    val courses = MutableLiveData<MutableList<Course>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val userActivityRepository = UserActivityRepository(getApplication())
            userActivityRepository.getUserActivity(courses, idUser)
        }
    }
}