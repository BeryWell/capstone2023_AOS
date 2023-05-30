package com.example.fitfume.model.data

import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("korName") val korName: String
)