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
    fun postUser(username: String, nom: String, email: String, password: String) {
        val queue = Volley.newRequestQueue(application)
        val user = JSONObject()
        try {
            //input your API parameters
            user.put("nom_utilisateur", username)
            user.put("nom", nom)
            user.put("email", email)
            user.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

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