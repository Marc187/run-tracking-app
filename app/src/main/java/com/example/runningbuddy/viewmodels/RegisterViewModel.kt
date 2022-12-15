package com.example.runningbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.runningbuddy.repositories.RegisterRepository
import org.json.JSONException
import org.json.JSONObject


class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    var username = ""
    var nom = ""
    var email = ""
    var password = ""

    // Get the Repo for register so we get its functions
    private val registerRepository = RegisterRepository(getApplication())

    // Create the jsonObj user then use the post function of the Repo
    fun createUser(){
        val user = JSONObject()
        try {
            //input your API parameters
            user.put("nom_utilisateur", username)
            user.put("nom", nom)
            user.put("email", email)
            user.put("password", password)
            registerRepository.postUser(user)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}