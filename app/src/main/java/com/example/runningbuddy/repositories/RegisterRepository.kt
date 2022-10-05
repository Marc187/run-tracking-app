package com.example.runningbuddy.repositories

import android.app.Application
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class RegisterRepository(private val application: Application) {
    fun postUser() {
        val queue = Volley.newRequestQueue(application)
        val user = JSONObject()
        try {
            //input your API parameters
            user.put("parameter", "value")
            user.put("parameter", "value")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val r = JsonObjectRequest(
            Request.Method.POST,
            "https://projet3-running-buddy.herokuapp.com/register", user,
            {
                TODO()
            },
            {
                TODO()
            })
        queue.add(r)
    }
}