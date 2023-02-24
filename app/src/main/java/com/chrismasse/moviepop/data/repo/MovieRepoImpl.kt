package com.chrismasse.moviepop.data.repo

import arrow.core.Either
import com.chrismasse.moviepop.data.dao.NetworkDao
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.data.mapper.MovieMapper
import com.chrismasse.moviepop.domain.entities.Movie
import com.chrismasse.moviepop.domain.error.Problem
import com.chrismasse.moviepop.domain.repository.MovieRepo

class MovieRepoImpl(private val networkDao: NetworkDao): MovieRepo {

    override suspend fun getMovie(id: Int): Either<Problem, MovieData> {

        return networkDao.fetchMovieDetails(id);

    }

    override suspend fun getPopularMoviesAtPage(page: Int): Either<Problem, List<MovieData>> {
        return networkDao.fetchPopularMovies(page);
    }

}