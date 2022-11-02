package com.example.runningbuddy.repositories

import android.app.Application
import android.content.Intent
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.LoginActivity
import com.example.runningbuddy.MainActivity
import org.json.JSONException
import org.json.JSONObject

class LoginRepository (private val application: Application) {

    fun postUser(email: String, password: String) {
        val queue = Volley.newRequestQueue(application)
        val user = JSONObject()
        try {
            user.put("email", email)
            user.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val r = JsonObjectRequest(
            Request.Method.POST,
            "https://projet3-running-buddy.herokuapp.com/login", user,
            {
                MainActivity.TOKEN = it.getString("token")
                MainActivity.userId = it.getInt("id")
                val i = Intent(application, MainActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                application.startActivity(i)
            },
            {
                println(it.message)
            })
        queue.add(r)
    }
}