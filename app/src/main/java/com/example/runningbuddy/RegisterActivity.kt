package com.example.runningbuddy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.runningbuddy.databinding.RegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvLoginFromRegister2.setOnClickListener{switchToLogin()}
        binding.btnRegister.setOnClickListener{register()}

    }

    private fun switchToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        this.startActivity(i)
    }

    private fun register() {
        println("Register")
    }
}