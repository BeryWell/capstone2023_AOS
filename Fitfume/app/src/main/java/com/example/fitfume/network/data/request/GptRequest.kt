package com.example.fitfume.network.data.request

import com.example.fitfume.model.GptContent

data class GptRequest(
    val model: String,
    val messages: List<GptContent>
)