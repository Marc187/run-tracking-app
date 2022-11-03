package com.example.runningbuddy.models

import android.graphics.Bitmap

data class Run (
    var id: Int,
    var id_utilisateur: Int,
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0
)
