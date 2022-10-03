package com.example.runningbuddy.repositories

import android.app.Application
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RegisterRepository(private val application: Application) {
    fun putUser() {

        val queue = Volley.newRequestQueue(application)
        val r = StringRequest(
            Request.Method.PUT,
            "TODO",
            {
                TODO()
            },
            {
                TODO()
            })
        queue.add(r)
    }
}