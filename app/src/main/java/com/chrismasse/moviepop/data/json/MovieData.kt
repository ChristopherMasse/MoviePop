package com.chrismasse.moviepop.data.json

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title:String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average")val voteAverage: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("genres") val genres: List<GenreJson>,
    @SerializedName("credits") val credits: CreditsWrapper,
    @SerializedName("images") val images: ImagesWrapper

)

