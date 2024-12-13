package com.example.kotlinplannerapp.signUp

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpActivityViewModel : ViewModel() {

    private val isSignUpSuccessLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var auth: FirebaseAuth = Firebase.auth

    companion object {
        private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    }

    fun getIsSignUpSuccessLiveData() : LiveData<Boolean> = isSignUpSuccessLiveData

     private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern = Pattern.compile(Companion.PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun arePasswordsTheSame(password1 : String, password2: String) : Boolean {
        val arePasswordsTheSame = password1 == password2
        val arePasswordsValid = isValidPassword(password1) && isValidPassword(password2)
        return arePasswordsTheSame.takeIf { arePasswordsValid } ?: false
    }

    fun signUp(email : String, password1 : String,password2 : String) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && arePasswordsTheSame(password1, password2)) {
            auth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener {
                isSignUpSuccessLiveData.postValue(it.isSuccessful)
            }
        } else {
            isSignUpSuccessLiveData.postValue(false)
        }
    }
}