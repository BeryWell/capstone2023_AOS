package com.example.fitfume.network.data.response

import com.example.fitfume.model.data.Note
import com.google.gson.annotations.SerializedName

data class FindPerfumeResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("topNotes") val topNotes: List<Note>,
    @SerializedName("heartNotes") val heartNotes: List<Note>,
    @SerializedName("baseNotes") val baseNotes: List<Note>,
    @SerializedName("imgUrl") val imgUrl: String
)