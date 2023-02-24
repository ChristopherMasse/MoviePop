package com.chrismasse.moviepop.data.json

import com.google.gson.annotations.SerializedName

data class CreditJson(
    @SerializedName("id") val id: Int,
    @SerializedName("name")val name: String,
    @SerializedName("character") val characterName: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("known_for_department") val knownForDept: String,
    @SerializedName("job") val job: String?,
)
