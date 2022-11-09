package com.example.runningbuddy.ui.enregistrercourse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.runningbuddy.models.RunPost
import com.example.runningbuddy.repositories.RunRepository

class EnregistrerCourseViewModel(val app: Application) : AndroidViewModel(app) {

    private var runRepository: RunRepository = RunRepository(app)

    fun insertRun(runPost: RunPost) {
        runRepository.insertRun(runPost)
    }
}