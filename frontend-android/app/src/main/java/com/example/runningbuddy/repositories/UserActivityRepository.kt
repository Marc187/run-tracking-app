package com.example.runningbuddy.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.models.RunGet
import com.google.gson.Gson

class UserActivityRepository (private val application: Application) {

    fun getUserActivity(courses: MutableLiveData<MutableList<RunGet>>) {
        val queue = Volley.newRequestQueue(application)
        val r = object : StringRequest(
            Method.GET,
            "${MainActivity.SRVURL}/activity",
            {
                val data = Gson().fromJson(it, Array<RunGet>::class.java)
                courses.value = data.toMutableList()
            },
            {
                println("ERREUR: /api/activity")
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap["Content-Type"] = "application/json";
                headerMap["Authorization"] = "Bearer ${MainActivity.TOKEN}";
                return headerMap
            }
        }
        queue.add(r)
    }
}