package com.example.fitfume.network.data.response

import com.example.fitfume.model.ReviewUserInfo

data class CreateReviewResponse(
    val id: Long,
    val perfumeVitality: String,
    val genderExpressionScale: String,
    val seasons: String,
    val fashionStyle: String,
    val description: String,
    val createdAt: String,
    val writeUser: ReviewUserInfo
)