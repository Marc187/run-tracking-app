package com.example.runningbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

@Suppress("RedundantVisibilityModifier")
class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    var nom = ""
    var username = ""
    var email = ""
    var password = ""


    fun createJsonRegister(){
        val user = JSONObject()
        try {
            //input your API parameters
            user.put("nom_utilisateur", username)
            user.put("nom", nom)
            user.put("email", email)
            user.put("password", password)
            // lancer requte volley du repo
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}