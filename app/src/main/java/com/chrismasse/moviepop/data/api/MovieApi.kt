package com.chrismasse.moviepop.data.api

import com.chrismasse.moviepop.ApiKeys
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.data.json.PageData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("{id}?api_key=${ApiKeys.MOVIEDB_API_KEY}&append_to_response=credits,images&include_image_language=en")
    suspend fun getMovieDetailsFull(@Path("id") movieId:Int): Response<MovieData>
    @GET("popular?api_key=${ApiKeys.MOVIEDB_API_KEY}")
    suspend fun getPopularMoviesAtPage(@Query("page") page:Int): Response<PageData>
}