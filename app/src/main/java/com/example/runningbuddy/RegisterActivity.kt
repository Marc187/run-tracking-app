package com.example.runningbuddy

import android.content.Intent
import android.os.Bundle
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
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        findViewById<EditText>(R.id.usernameRegister).setText(registerViewModel.username)
        findViewById<EditText>(R.id.usernameRegister).addTextChangedListener {
            registerViewModel.username = it.toString()
        }

        btnRegister.setOnClickListener{
          registerViewModel.createJsonRegister()
        }

    }

    private fun switchToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        this.startActivity(i)
    }
}