package com.chrismasse.moviepop.data.json

import com.google.gson.annotations.SerializedName

data class PageData(
    @SerializedName("page") val title:String,
    @SerializedName("results") val results: List<MovieData>

)
