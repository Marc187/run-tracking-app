package com.example.runningbuddy

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val tvToLogin : TextView = findViewById(R.id.tvLoginFromRegister2)
        tvToLogin.setOnClickListener{switchToLogin()}
        val btnRegister : Button =  findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener{register()}

    }

    private fun switchToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        this.startActivity(i)
    }

    private fun register() {
        println("Register")
    }
}