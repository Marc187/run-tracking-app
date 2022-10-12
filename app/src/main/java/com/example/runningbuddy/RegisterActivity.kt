package com.example.runningbuddy

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.runningbuddy.databinding.RegisterBinding
import com.example.runningbuddy.repositories.RegisterRepository
import com.example.runningbuddy.viewmodels.RegisterViewModel


class RegisterActivity : AppCompatActivity() {
    private lateinit var registerViewModel : RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        val tvLoginFromRegister2 = findViewById<TextView>(R.id.tvLoginFromRegister2)
        tvLoginFromRegister2.setOnClickListener{switchToLogin()}
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        // Set the viewmodel to not get deleted if we destroyed this activity
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)


        // Get info from edit text and attach it to the Viemodel
        findViewById<EditText>(R.id.usernameRegister).setText(registerViewModel.username)
        findViewById<EditText>(R.id.usernameRegister).addTextChangedListener {
            registerViewModel.username = it.toString()
        }
        findViewById<EditText>(R.id.prenomNomRegister).setText(registerViewModel.nom)
        findViewById<EditText>(R.id.prenomNomRegister).addTextChangedListener {
            registerViewModel.nom = it.toString()
        }
        findViewById<EditText>(R.id.emailRegister).setText(registerViewModel.email)
        findViewById<EditText>(R.id.emailRegister).addTextChangedListener {
            registerViewModel.email = it.toString()
        }
        findViewById<EditText>(R.id.passwordRegister).setText(registerViewModel.password)
        findViewById<EditText>(R.id.passwordRegister).addTextChangedListener {
            registerViewModel.password = it.toString()
        }


        btnRegister.setOnClickListener{
            checkInput()    
        }

    }

    private fun switchToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        this.startActivity(i)
    }

    fun checkInput() {
        val email = this.findViewById<EditText>(R.id.emailRegister)
        val password = this.findViewById<EditText>(R.id.passwordRegister)
        val name = this.findViewById<EditText>(R.id.prenomNomRegister)
        val username = this.findViewById<EditText>(R.id.usernameRegister)

        if (TextUtils.isEmpty(username.text.toString().trim())) {
            username.error = "Veuillez entrer un nom d'utilisateur"
        } else if (TextUtils.isEmpty(name.text.toString().trim())) {
            name.error = "Veuillez entrer votre nom"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString().trim()).matches()) {
            email.error = "Format du Email invalide"
        } else if (password.text.toString().trim().length < 6) {
            password.error = "Votre mot de passe doit contenir au moins 6 charactères"
        } else if (TextUtils.isEmpty(password.text.toString().trim())) {
            password.error = "Veuillez entrer un mot de passe"
        } else {
            registerViewModel.createUser()
        }
    }
}