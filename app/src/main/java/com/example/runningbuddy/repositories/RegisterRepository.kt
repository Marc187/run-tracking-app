package com.example.runningbuddy.repositories

import android.app.Application
import android.content.Intent
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.LoginActivity
import org.json.JSONException
import org.json.JSONObject


class RegisterRepository(private val application: Application) {
    fun postUser(user: JSONObject) {
        val queue = Volley.newRequestQueue(application)
        val r = JsonObjectRequest(
            Request.Method.POST,
            "https://projet3-running-buddy.herokuapp.com/register", user,
            {
                val i = Intent(application, LoginActivity::class.java)
                application.startActivity(i)
            },
            {
                println(it.message)
            })
        queue.add(r)
    }
}