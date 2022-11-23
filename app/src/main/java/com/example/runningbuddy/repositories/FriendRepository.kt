package com.example.runningbuddy.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.models.User
import com.google.gson.Gson

class FriendRepository (private val application: Application) {
    fun getUsersByName(users: MutableLiveData<MutableList<User>>, name: String) {
        val queue = Volley.newRequestQueue(application)
        val r = StringRequest(
            Request.Method.GET,
            "${MainActivity.SRVURL}/users/$name",
            {
                val data = Gson().fromJson(it, Array<User>::class.java)
                users.value = data.toMutableList()
            },
            {
                println("ERREUR: /api/activity")
            })

        queue.add(r)
    }
}