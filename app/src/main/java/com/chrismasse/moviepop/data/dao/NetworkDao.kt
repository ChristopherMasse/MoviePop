package com.chrismasse.moviepop.data.dao

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.chrismasse.moviepop.data.api.MovieApi
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.domain.error.Problem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.Exception
import java.net.SocketTimeoutException
import java.util.*
import java.util.concurrent.TimeUnit

class NetworkDao {
    private val client: OkHttpClient

    private val retrofit: Retrofit

    private val BASE_URL = "https://api.themoviedb.org/3/movie/"

    private val movieApi: MovieApi

    private val CONNECT_TIMEOUT = 15L;

    private val READ_TIMEOUT = 15L;

    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        client = OkHttpClient().newBuilder()
//            .addInterceptor(AuthInterceptor())
            .addInterceptor(interceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

        movieApi = retrofit.create(MovieApi::class.java)

    }

    suspend fun fetchMovieDetails(id: Int): Either<Problem, MovieData> {
        try {
            val r = movieApi.getMovieDetailsFull(id)

            return if (r.isSuccessful && r.body() != null) {
                r.body()!!.right()
            } else {
                Problem.NetworkProblem("Could not retrieve data from Network. Please check connection")
                    .left()
            }
        }catch (c:SocketTimeoutException){
            return Problem.NetworkProblem("Connection timed out. Please try again later").left()
        } catch (e: Exception){
            return Problem.NetworkProblem("Could not retrieve data from Network. Please check connection").left()
        }

    }

    suspend fun fetchPopularMovies(page: Int): Either<Problem, List<MovieData>> {
        try{
            val r = movieApi.getPopularMoviesAtPage(page)
            return if (r.isSuccessful){
                if (r.body() != null){
                    r.body()!!.results.right()
                } else{
                    Collections.emptyList<MovieData>().right();
                }
            }else{
                Problem.NetworkProblem("Could not retrieve data from Network. Please check connection!").left()
            }
        }catch (c:SocketTimeoutException){
            return Problem.NetworkProblem("Connection timed out. Please try again later").left()
        }catch (e: Exception){
            Timber.e(e)
            return Problem.NetworkProblem("Could not retrieve data from Network. Please check connection").left()
        }
    }
}