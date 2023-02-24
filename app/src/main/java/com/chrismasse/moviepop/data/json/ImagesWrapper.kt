package com.chrismasse.moviepop.data.json

import com.google.gson.annotations.SerializedName

data class ImagesWrapper(
    @SerializedName("backdrops") val backdrops: List<ImageJson>,
    @SerializedName("logos") val logos: List<ImageJson>,
    @SerializedName("posters") val posters: List<ImageJson>
)
