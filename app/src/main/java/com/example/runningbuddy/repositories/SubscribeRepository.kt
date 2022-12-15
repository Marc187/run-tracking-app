package com.example.runningbuddy.repositories

import android.app.Application
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity

class SubscribeRepository (private val application: Application) {

    fun addSubscribe(userId: Int) {
        val queue = Volley.newRequestQueue(application)
        val r = object : StringRequest(
            Method.POST,
            "${MainActivity.SRVURL}/subscribe/$userId",
            {
                println(it)
            },
            {
                println("ERREUR: /api/subscribe")
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

    fun deleteSubscribe(userId: Int) {
        val queue = Volley.newRequestQueue(application)
        val r = object : StringRequest(
            Method.DELETE,
            "${MainActivity.SRVURL}/subscribe/$userId",
            {
                println(it)
            },
            {
                println("ERREUR: /api/subscribe")
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