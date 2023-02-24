package com.chrismasse.moviepop.data.json

import com.google.gson.annotations.SerializedName

data class GenreJson(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
