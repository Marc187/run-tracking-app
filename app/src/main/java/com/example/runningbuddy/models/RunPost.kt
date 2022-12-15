package com.example.runningbuddy.models

data class RunPost (
    var id_utilisateur: Int,
    var timeStamps: String,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0
)
