package com.example.fitfume.model

data class SearchResultEvent(
    val brand: String,
    val title: String,
    var star: Float,
    var count: String
)