package com.example.runningbuddy.repositories

import android.app.Application
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity

class LikeRepository (private val application: Application) {

    fun addLike(courseId: Int) {
        val queue = Volley.newRequestQueue(application)
        val r = object : StringRequest(
            Method.POST,
            "${MainActivity.SRVURL}/like/$courseId/${MainActivity.userId}",
            {
                println("${MainActivity.SRVURL}/like/$courseId/${MainActivity.userId}")
                println("Ajout like")
                println(it)
            },
            {
                println("ERREUR: /api/like")
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

    fun deleteLike(courseId: Int) {
        val queue = Volley.newRequestQueue(application)
        val r = object : StringRequest(
            Method.DELETE,
            "${MainActivity.SRVURL}/like/$courseId/${MainActivity.userId}",
            {
                println("${MainActivity.SRVURL}/like/$courseId/${MainActivity.userId}")
                println("Suppression like")
                println(it)
            },
            {
                println("ERREUR: /api/like")
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