package com.example.runningbuddy.repositories

import android.app.Application
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.converters.Converters
import com.example.runningbuddy.models.RunPost
import org.json.JSONException
import org.json.JSONObject


class RunRepository (private val application: Application) {
    private var converters: Converters = Converters()

    fun insertRun(runPost: RunPost) {
        val queue = Volley.newRequestQueue(application)
        val runObject = JSONObject()
        try {
            runObject.put("id_utilisateur", runPost.id_utilisateur)
                                        //convert si run.img est pas null
            runObject.put("img", runPost.img?.let { converters.fromBitMap(it) })
            runObject.put("timestamp", runPost.timeStamps)
            runObject.put("avgSpeedInKMH", runPost.avgSpeedInKMH)
            runObject.put("distanceInMeters", runPost.distanceInMeters)
            runObject.put("timeInMillis", runPost.timeInMillis)
            runObject.put("caloriesBurned", runPost.caloriesBurned)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val r = object : JsonObjectRequest(
            Method.POST,
            "https://projet3-running-buddy.herokuapp.com/course", runObject,
            {
                println(it)
            },
            {
                println(it.message)
            })
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