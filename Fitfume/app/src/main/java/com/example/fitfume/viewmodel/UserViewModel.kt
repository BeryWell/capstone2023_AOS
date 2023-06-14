package com.example.fitfume.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitfume.network.data.request.LoginRequest
import com.example.fitfume.network.data.request.SignUpRequest
import com.example.fitfume.repository.UserRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class UserViewModel : ViewModel() {
    private val userRepository = UserRepositoryImpl()

    companion object{
        var accessToken: String = ""
    }

    private var _isLoginSuccess = MutableLiveData<String>()
    val isLoginSuccess: LiveData<String>
        get() = _isLoginSuccess

    init{
        _isLoginSuccess.value = ""
    }

    @SuppressLint("CheckResult")
    fun signup(request: SignUpRequest){
        userRepository.signup(request).subscribeBy (
            onSuccess = {
                Log.d("Loginlhj", "signupToken: $it")
            },
            onError = {
                it.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    fun login(request: LoginRequest){
        userRepository.login(request).subscribeBy (
            onSuccess = {
                updateToken(it.token)
                _isLoginSuccess.value = it.token
                Log.d("Loginlhj", "token: $it")
            },
            onError = {
                it.printStackTrace()
            })
    }

    private fun updateToken(inputToken: String?) {
        Log.d("lhj", "updateToken: $inputToken")
        if (inputToken != null) {
            accessToken = inputToken
        } else {
            accessToken = ""
        }

    }
}