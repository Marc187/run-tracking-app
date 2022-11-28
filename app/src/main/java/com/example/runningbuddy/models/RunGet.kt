package com.example.runningbuddy.models

data class RunGet(
    val id: Int,
    val id_utilisateur: Int,
    val nom: String,
    //var img: ByteArray,
    var timeStamps: String,
    var avgSpeedInKMH: Float,
    var distanceInMeters: Float,
    var timeInMillis: Long,
    var caloriesBurned: Int,
    var liked: Boolean
)
