package com.example.runningbuddy.models

import java.math.BigInteger

data class RunGet(
    val id: Int,
    val id_utilisateur: Int,
    val nom: String,
    var img: ByteArray,
    var timeStamps: Long,
    var avgSpeedInKMH: Float,
    var distanceInMeters: Int,
    var timeInMillis: Long,
    var caloriesBurned: Int,
    var liked: Boolean
)
