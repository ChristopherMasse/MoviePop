package com.chrismasse.moviepop.data.json

import com.google.gson.annotations.SerializedName

data class ImageJson(
    @SerializedName("aspect_ratio") val aspectRatio: Float,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("file_path") val filePath: String
)
