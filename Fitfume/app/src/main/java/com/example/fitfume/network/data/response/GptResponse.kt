package com.example.fitfume.network.data.response

import com.example.fitfume.model.GptChoices
import com.example.fitfume.model.GptContent
import com.google.gson.annotations.SerializedName

data class GptResponse(
    @SerializedName("choices")
    val choices: List<GptChoices>
)