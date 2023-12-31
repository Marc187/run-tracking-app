package com.example.runningbuddy.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.runningbuddy.models.RunGet
import com.example.runningbuddy.repositories.LikeRepository
import com.example.runningbuddy.repositories.UserActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val app: Application) : AndroidViewModel(app) {
    val courses = MutableLiveData<MutableList<RunGet>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val userActivityRepository = UserActivityRepository(getApplication())
            userActivityRepository.getUserActivity(courses)
        }
    }

    fun updateLike(indexCourse: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val course = courses.value?.get(indexCourse)
            val likeRepository = LikeRepository(getApplication())

            if (course!!.liked) {
                likeRepository.deleteLike(course.id)
                course.liked = false
            } else {
                likeRepository.addLike(course.id)
                course.liked = true
            }
            courses.value?.get(indexCourse)?.let { println(it.liked) }
        }
    }
}