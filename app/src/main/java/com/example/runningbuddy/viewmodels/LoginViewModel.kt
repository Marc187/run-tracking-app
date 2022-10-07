package com.example.runningbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.json.JSONException
import org.json.JSONObject

@Suppress("RedundantVisibilityModifier")
class LoginViewModel (application: Application) : AndroidViewModel(application) {
    var email = ""
    var password = ""


    fun createJsonRegister(){
        val user = JSONObject()
        try {
            user.put("email", email)
            user.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}