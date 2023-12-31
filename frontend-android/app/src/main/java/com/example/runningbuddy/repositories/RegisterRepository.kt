package com.example.runningbuddy.repositories

import android.app.Application
import android.content.Intent
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.LoginActivity
import org.json.JSONObject


class RegisterRepository(private val application: Application) {
    // Call the api to post a user in the database
    fun postUser(user: JSONObject) {
        val queue = Volley.newRequestQueue(application)
        val r = JsonObjectRequest(
            Request.Method.POST,
            "https://projet3-running-buddy.herokuapp.com/register", user,
            {
                // if successful switch to the login activity so the user can connect
                val i = Intent(application, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                application.startActivity(i)
            },
            {
                println(it.message)
            })
        queue.add(r)
    }
}