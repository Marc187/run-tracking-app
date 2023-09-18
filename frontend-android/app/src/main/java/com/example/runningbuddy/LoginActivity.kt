package com.example.runningbuddy

import com.example.runningbuddy.viewmodels.LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // va chercher le viewmodel et et les button pour pouvoir leur attribuer des onclick
        val tvRegisterFromLogin = findViewById<TextView>(R.id.lienInscription)
        tvRegisterFromLogin.setOnClickListener{switchToRegister()}
        val btnConnexion = findViewById<Button>(R.id.btnConnexion)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val geterrorOutput: TextView = this.findViewById(R.id.error_output)

        // Relie les champs du login au loginViewmodel
        findViewById<EditText>(R.id.emailLogin).setText(loginViewModel.email)
        findViewById<EditText>(R.id.emailLogin).addTextChangedListener {
            loginViewModel.email = it.toString()
        }
        findViewById<EditText>(R.id.passwordLogin).setText(loginViewModel.password)
        findViewById<EditText>(R.id.passwordLogin).addTextChangedListener {
            loginViewModel.password = it.toString()
        }

        // permet de rajouter l'option de connection avec des options de validation
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