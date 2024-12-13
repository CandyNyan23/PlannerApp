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
    private var emailEditText: EditText? = null
    private var password1EditText: EditText? = null
    private var password2EditText: EditText? = null

    private val signUpObserver: Observer<Boolean> = Observer { isSignUpSuccessful ->
        if (isSignUpSuccessful) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Toast.makeText(
                this,
                R.string.wrong_email_or_password,
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
        SignUpActivityViewModel.signUp(emailEditText?.text.toString(), password1EditText?.text.toString(), password2EditText?.text.toString())
    }

    private fun bindViews() {
        emailEditText = findViewById(R.id.email)
        password1EditText = findViewById(R.id.password)
        password2EditText = findViewById(R.id.confirm_password)
        signUpButton = findViewById(R.id.button_sign_up)
    }

    private fun initObservers() {
        SignUpActivityViewModel.getIsSignUpSuccessLiveData().observe(this, signUpObserver)
    }
}