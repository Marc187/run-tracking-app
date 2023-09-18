package com.example.runningbuddy.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.models.User
import com.google.gson.Gson

class UsersFriendRepository (private val application: Application) {

    fun getUsersByName(users: MutableLiveData<MutableList<User>>, userId: Int, name: String) {
        val queue = Volley.newRequestQueue(application)
        val r = object : StringRequest(
            Method.GET,
            "${MainActivity.SRVURL}/users/$userId/$name",
            {
                val data = Gson().fromJson(it, Array<User>::class.java)
                users.value = data.toMutableList()
            },
            {
                println("ERREUR: /api/activity")
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