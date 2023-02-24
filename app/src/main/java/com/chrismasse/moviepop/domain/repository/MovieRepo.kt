package com.chrismasse.moviepop.domain.repository

import arrow.core.Either
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.domain.entities.Movie
import com.chrismasse.moviepop.domain.error.Problem

interface MovieRepo {

    suspend fun getMovie(id:Int): Either<Problem, MovieData>

    suspend fun getPopularMoviesAtPage(page:Int): Either<Problem, List<MovieData>>

}