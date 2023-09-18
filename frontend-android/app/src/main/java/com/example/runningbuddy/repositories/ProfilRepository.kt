package com.example.runningbuddy.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.models.StatsCourse
import com.google.gson.Gson

class ProfilRepository (private val application: Application) {

    fun getCoursesUser(statscourse: MutableLiveData<MutableList<StatsCourse>>) {

        val queue = Volley.newRequestQueue(application)
        val bearerToken = "Bearer " + MainActivity.TOKEN;
        val r = object : StringRequest(
            Request.Method.GET,
            "https://projet3-running-buddy.herokuapp.com/statsCourses/${MainActivity.userId}",
            {
                print(it)
                val arrayStats = Gson().fromJson(it, Array<StatsCourse>::class.java)
                statscourse.value = arrayStats.toMutableList()

            },
            {
                println("ERREUR: /api/statsCourses/X")
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", bearerToken);
                return headerMap
            }
        }

        queue.add(r)
    }
}