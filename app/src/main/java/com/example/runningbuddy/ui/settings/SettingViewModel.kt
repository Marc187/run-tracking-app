package com.example.runningbuddy.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.runningbuddy.repositories.SettingRepository


class SettingViewModel (application: Application) : AndroidViewModel(application) {

    private var settingRepository: SettingRepository = SettingRepository(application)
    var oldPassword = ""
    var newPassword = ""

    fun resetPassword(){
        settingRepository.resetPassword(oldPassword, newPassword)
    }
}