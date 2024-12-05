package com.example.kotlinplannerapp.login

import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginActivityViewModel : ViewModel() {

    private var isLoginSuccessLiveData : MutableLiveData<Boolean> = MutableLiveData()

    fun getIsLoginSuccessLiveData() : LiveData<Boolean> = isLoginSuccessLiveData

    fun login(email : String, password : String) {
        if (email.isNotEmpty() && password.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isLoginSuccessLiveData = MutableLiveData(true)
        } else {
            isLoginSuccessLiveData = MutableLiveData(false)
        }
    }
}