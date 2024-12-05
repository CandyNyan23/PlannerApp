package com.example.kotlinplannerapp.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivityViewModel : ViewModel() {

    private val isLoginSuccessLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var auth: FirebaseAuth = Firebase.auth

    fun getIsLoginSuccessLiveData() : LiveData<Boolean> = isLoginSuccessLiveData

    fun isValidPassword(password: String?): Boolean {

        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password.toString())

        return matcher.matches()
    }

    fun login(email : String, password : String) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && isValidPassword(password)) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                isLoginSuccessLiveData.postValue(it.isSuccessful)
            }
        } else { 
            isLoginSuccessLiveData.postValue(false)
        }
    }
}