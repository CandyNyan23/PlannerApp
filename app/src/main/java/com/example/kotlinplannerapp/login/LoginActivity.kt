package com.example.kotlinplannerapp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.kotlinplannerapp.R
import com.example.kotlinplannerapp.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private var loginButton: Button? = null
    private var email: EditText? = null
    private var password: EditText? = null

    private val loginObserver: Observer<Boolean> = Observer { isLoginSuccessful ->
        if (isLoginSuccessful) {
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            Toast.makeText(
                this,
                "Enter valid email address and/or password.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val loginActivityViewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginScreen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObservers()
        bindViews()
        loginButton?.setOnClickListener(::onLoginButtonClick)
    }

    private fun onLoginButtonClick(view: View) {
        loginActivityViewModel.login(email?.text.toString(), password?.text.toString())
    }

    private fun bindViews() {
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.button_login)
    }

    private fun initObservers() {
        loginActivityViewModel.getIsLoginSuccessLiveData().observe(this, loginObserver)
    }
}