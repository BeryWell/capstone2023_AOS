package com.example.fitfume.model

data class PerfumeReviewEvent (
    var nickname:String = "",
    val gender: String,
    val style: String,
    val season: String,
    val date: String,
    val perfumeTitle: String,
    val perfumeBrand: String,
    val description: String,
    var star: Float = 0.0F
)