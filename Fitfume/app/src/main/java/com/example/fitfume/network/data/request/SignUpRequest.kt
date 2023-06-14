package com.example.fitfume.network.data.request

data class SignUpRequest(
    val email: String,
    val gender: String,
    val password: String,
    val username: String
)