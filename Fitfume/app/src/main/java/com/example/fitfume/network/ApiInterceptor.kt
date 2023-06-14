package com.example.fitfume.network

import android.util.Log
import com.example.fitfume.viewmodel.UserViewModel.Companion.accessToken
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if(accessToken.isNotEmpty()){
            val token = "Bearer $accessToken"
            builder.addHeader("Authorization", token)

        }else{
            Log.d("TAG", "intercept: token null")
        }
        return chain.proceed(builder.build())
    }
}