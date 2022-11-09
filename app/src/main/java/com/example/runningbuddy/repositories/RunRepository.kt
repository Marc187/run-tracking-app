package com.example.runningbuddy.repositories

import android.app.Application
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.converters.Converters
import com.example.runningbuddy.models.Run
import org.json.JSONException
import org.json.JSONObject


class RunRepository (private val application: Application) {
    private var converters: Converters = Converters()

    fun insertRun(run: Run) {
        val queue = Volley.newRequestQueue(application)
        val runObject = JSONObject()
        try {
            runObject.put("id_utilisateur", run.id_utilisateur)
                                        //convert si run.img est pas null
            runObject.put("img", run.img?.let { converters.fromBitMap(it) })
            runObject.put("timestamp", run.timestamp)
            runObject.put("avgSpeedInKMH", run.avgSpeedInKMH)
            runObject.put("distanceInMeters", run.distanceInMeters)
            runObject.put("timeInMillis", run.timeInMillis)
            runObject.put("caloriesBurned", run.caloriesBurned)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val r = JsonObjectRequest(
            Request.Method.POST,
            "https://projet3-running-buddy.herokuapp.com/ TODO() ", runObject,
            {
                println(it)
            },
            {
                println(it.message)
            })
        queue.add(r)
    }
}