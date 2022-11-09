package com.example.runningbuddy.ui.enregistrercourse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.runningbuddy.models.Run
import com.example.runningbuddy.repositories.RunRepository

class EnregistrerCourseViewModel(val app: Application) : AndroidViewModel(app) {

    private var runRepository: RunRepository = RunRepository(app)
    
    fun insertRun(run: Run) {
        runRepository.insertRun(run)
    }
}