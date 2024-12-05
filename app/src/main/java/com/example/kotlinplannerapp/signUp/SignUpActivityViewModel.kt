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

    fun getIsSignUpSuccessLiveData() : LiveData<Boolean> = isSignUpSuccessLiveData

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password.toString())

        return matcher.matches()
    }

    fun isSamePassword(password1 : String, password2: String) : Boolean {
        if (isValidPassword(password1) && isValidPassword(password2)) {
            if (password1 == password2) {
                return true
            } else {
                return false
            }
        }
        return false
    }

    fun signUp(email : String, password1 : String,password2 : String) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && isSamePassword(password1, password2)) {
            auth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener {
                isSignUpSuccessLiveData.postValue(it.isSuccessful)
            }
        } else {
            isSignUpSuccessLiveData.postValue(false)
        }
    }

}