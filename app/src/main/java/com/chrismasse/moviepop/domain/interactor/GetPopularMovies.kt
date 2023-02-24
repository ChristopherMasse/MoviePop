package com.chrismasse.moviepop.domain.interactor

import arrow.core.Either
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.domain.error.Problem
import com.chrismasse.moviepop.domain.repository.MovieRepo

class GetPopularMovies(private val repo: MovieRepo) {

    suspend fun run(page:Int): Either<Problem, List<MovieData>> {
        return repo.getPopularMoviesAtPage(page)
    }
}