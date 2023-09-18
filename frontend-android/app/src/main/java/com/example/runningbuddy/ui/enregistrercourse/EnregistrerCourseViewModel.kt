package com.example.runningbuddy.ui.enregistrercourse

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.runningbuddy.models.RunPost
import com.example.runningbuddy.repositories.RunRepository

class EnregistrerCourseViewModel(val app: Application) : AndroidViewModel(app) {
    var idCourse = MutableLiveData<Int>()
    private var runRepository: RunRepository = RunRepository(app)
    var imgCourseTest = MutableLiveData<Bitmap>()

    fun insertRun(runPost: RunPost, imageBitmap: Bitmap) {
        runRepository.insertRun(runPost, idCourse, imageBitmap)
    }
}