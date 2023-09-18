package com.example.runningbuddy.repositories

import android.app.Application
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity
import org.json.JSONException
import org.json.JSONObject

class SettingRepository (private val application: Application) {

    fun resetPassword(oldPassword: String, newPassword: String) {
        val bearerToken = "Bearer " + MainActivity.TOKEN;
        val queue = Volley.newRequestQueue(application)
        val user = JSONObject()
        try {
            user.put("oldpassword", oldPassword)
            user.put("newpassword", newPassword)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val r = object : JsonObjectRequest(
            Request.Method.PUT,
            "https://projet3-running-buddy.herokuapp.com/resetPassword", user,
            {
                println("Mot de passe changé avec succès!")
            },
            {
                println(it.message)
            })

        {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", bearerToken);
                return headerMap
            }
        }

        queue.add(r)
    }
}