package com.example.runningbuddy.repositories

import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.models.Course
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class UserActivityRepository (private val application: Application) {

    fun getUserActivity(courses: MutableLiveData<MutableList<Course>>, userId: Int) {
        val queue = Volley.newRequestQueue(application)
        val r = object : StringRequest(
            Method.GET,
            "${MainActivity.SRVURL}/activity/$userId",
            {
                print(it)
                val data = Gson().fromJson(it, Array<Course>::class.java)
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