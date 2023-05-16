package com.example.fitfume.network.chatgpt

import android.util.Log
import com.example.fitfume.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class GptApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Authorization", "Bearer ${BuildConfig.CHATGPT_API_KEY}")

        return chain.proceed(builder.build())
    }
}