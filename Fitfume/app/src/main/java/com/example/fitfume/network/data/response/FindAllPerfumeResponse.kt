package com.example.fitfume.network.data.response

data class FindAllPerfumeResponse(
    val name: String,
    val brand: String,
    val topNotes: List<String>,
    val heartNotes: List<String>,
    val baseNotes: List<String>,
    val imgUrl: String
)