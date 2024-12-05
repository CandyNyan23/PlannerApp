package com.example.kotlinplannerapp.signUp

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
import com.example.kotlinplannerapp.login.LoginActivity

class SignUpActivity : AppCompatActivity() {

    private var signUpButton: Button? = null
    private var email: EditText? = null
    private var password1: EditText? = null
    private var password2: EditText? = null

    private val signUpObserver: Observer<Boolean> = Observer { isSignUpSuccessful ->
        if (isSignUpSuccessful) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Toast.makeText(
                this,
                "Enter valid email address and/or password.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val SignUpActivityViewModel: SignUpActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signupScreen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObservers()
        bindViews()
        signUpButton?.setOnClickListener(::onLoginButtonClick)
    }

    private fun onLoginButtonClick(view: View) {
        SignUpActivityViewModel.signUp(email?.text.toString(), password1?.text.toString(), password2?.text.toString())
    }

    private fun bindViews() {
        email = findViewById(R.id.email)
        password1 = findViewById(R.id.password1)
        password2 = findViewById(R.id.password2)
        signUpButton = findViewById(R.id.button_sign_up)
    }

    private fun initObservers() {
        SignUpActivityViewModel.getIsSignUpSuccessLiveData().observe(this, signUpObserver)
    }
}