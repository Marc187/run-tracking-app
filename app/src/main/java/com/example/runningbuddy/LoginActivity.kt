package com.example.runningbuddy

import LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val tvRegisterFromLogin = findViewById<TextView>(R.id.lienInscription)
        tvRegisterFromLogin.setOnClickListener{switchToRegister()}
        val btnConnexion = findViewById<Button>(R.id.btnConnexion)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val geterrorOutput: TextView = this.findViewById(R.id.error_output)

        findViewById<EditText>(R.id.emailLogin).setText(loginViewModel.email)
        findViewById<EditText>(R.id.emailLogin).addTextChangedListener {
            loginViewModel.email = it.toString()
        }
        findViewById<EditText>(R.id.passwordLogin).setText(loginViewModel.password)
        findViewById<EditText>(R.id.passwordLogin).addTextChangedListener {
            loginViewModel.password = it.toString()
        }

        btnConnexion.setOnClickListener{
            val emailLogin = findViewById<EditText>(R.id.emailLogin)
            val passwordLogin = findViewById<EditText>(R.id.passwordLogin)
            if((emailLogin.text.toString() == "") || passwordLogin.text.toString() == ""){
                geterrorOutput.text = "REMPLISSEZ TOUS LES CHAMPS"
            }
            else{
                loginViewModel.postUser()
            }
        }



    }
    private fun switchToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        this.startActivity(i)
    }

}