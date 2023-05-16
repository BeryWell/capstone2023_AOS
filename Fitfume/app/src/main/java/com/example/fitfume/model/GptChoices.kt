package com.example.fitfume.model

import com.google.gson.annotations.SerializedName

data class GptChoices(
    @SerializedName("message")
    val message: GptMessage
)