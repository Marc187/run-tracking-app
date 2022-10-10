package com.example.runningbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.runningbuddy.repositories.LoginRepository
import com.example.runningbuddy.repositories.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

@Suppress("RedundantVisibilityModifier")
class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    var username = ""
    var nom = ""
    var email = ""
    var password = ""

    val registerRepository = RegisterRepository(getApplication())

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