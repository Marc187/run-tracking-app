package com.example.runningbuddy.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class HomeViewModelFactory(
    val application: Application,
    private val idUser: Int
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(this.application, idUser) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}