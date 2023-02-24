package com.chrismasse.moviepop.data.json

import com.google.gson.annotations.SerializedName

data class CreditsWrapper(
    @SerializedName("cast") val cast:List<CreditJson>
)
