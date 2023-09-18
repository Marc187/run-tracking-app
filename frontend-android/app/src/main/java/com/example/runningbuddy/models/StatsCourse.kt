package com.example.runningbuddy.models

data class StatsCourse(
    var id_utilisateur: Int,
    var totdist: Float,
    var totcalorie: Float,
    var avgspeed: Float,
    var tottemps: Float,
    var mois: String
)
