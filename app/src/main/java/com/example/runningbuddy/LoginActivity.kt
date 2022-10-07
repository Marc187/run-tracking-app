package com.example.runningbuddy

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.runningbuddy.databinding.LoginBinding
import com.example.runningbuddy.models.User
import com.example.runningbuddy.repositories.LoginRepository
import com.example.runningbuddy.viewmodels.LoginViewModel
import com.example.runningbuddy.viewmodels.RegisterViewModel
import com.google.gson.Gson


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val tvRegisterFromLogin = findViewById<TextView>(R.id.lienInscription)
        tvRegisterFromLogin.setOnClickListener{switchToRegister()}
        val btnConnexion = findViewById<Button>(R.id.btnConnexion)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        findViewById<EditText>(R.id.emailLogin).setText(loginViewModel.email)
        findViewById<EditText>(R.id.emailLogin).addTextChangedListener {
            loginViewModel.email = it.toString()
        }
        findViewById<EditText>(R.id.passwordLogin).setText(loginViewModel.password)
        findViewById<EditText>(R.id.passwordLogin).addTextChangedListener {
            loginViewModel.password = it.toString()
        }

        btnConnexion.setOnClickListener{
            loginViewModel.createJsonRegister()
        }



    }
    private fun switchToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        this.startActivity(i)
    }

}