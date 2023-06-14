package com.example.fitfume.network.data.request

data class CreateReviewRequest(
    val description: String,
    val fashionStyle: Int,
    val genderExpressionScale: Int,
    val perfumeId: Int,
    val perfumeVitality: Int,
    val seasons: Int
)