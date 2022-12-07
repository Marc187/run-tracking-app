package com.example.runningbuddy.repositories

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.converters.Converters
import com.example.runningbuddy.models.RunPost
import com.example.runningbuddy.ui.enregistrercourse.EnregistrerCourseFragment
import org.json.JSONException
import org.json.JSONObject


class RunRepository (private val application: Application) {
    private var converters: Converters = Converters()

    fun insertRun(runPost: RunPost, id_course: MutableLiveData<Int>, image: Bitmap) {


        val queue = Volley.newRequestQueue(application)
        val runObject = JSONObject()
        try {
            runObject.put("id_utilisateur", runPost.id_utilisateur)
            runObject.put("timeStamps", runPost.timeStamps)
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
                // EnregistrerCourseFragment.id_course = it.getInt("id_course")
                id_course.postValue(it.getInt("id_course"))
                println(it.getInt("id_course"))

                val imgRepo = RunImageRepository(application)
                imgRepo.uploadImage(it.getInt("id_course"), image)
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