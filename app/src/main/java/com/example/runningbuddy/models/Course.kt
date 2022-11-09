package com.example.runningbuddy.models

data class Course(
    val id: Int,
    val id_utilisateur: Int,
    val nom: String,
    val distance: Float,
    val duree: String,
    val date: String,
    val liked: Boolean
)
