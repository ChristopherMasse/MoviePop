package com.chrismasse.moviepop.domain.interactor

import arrow.core.Either
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.domain.error.Problem
import com.chrismasse.moviepop.domain.repository.MovieRepo

class GetMovieDetails(private val repo: MovieRepo){
    suspend fun run(movieId: Int): Either<Problem, MovieData> {
        return repo.getMovie(movieId)
    }
}