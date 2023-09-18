package com.example.runningbuddy.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.runningbuddy.models.StatsCourse
import com.example.runningbuddy.repositories.ProfilRepository


class ProfileViewModel(val app: Application) : AndroidViewModel(app) {

    var statscourse: MutableLiveData<MutableList<StatsCourse>> = MutableLiveData(mutableListOf())

    fun getCoursesUser(){
        val profilRepository = ProfilRepository(getApplication())
        profilRepository.getCoursesUser(statscourse)
    }

}