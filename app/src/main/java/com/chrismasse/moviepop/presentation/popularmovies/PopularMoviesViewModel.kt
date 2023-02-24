package com.chrismasse.moviepop.presentation.popularmovies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chrismasse.moviepop.data.dao.NetworkDao
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.data.repo.MovieRepoImpl
import com.chrismasse.moviepop.domain.error.Problem
import com.chrismasse.moviepop.domain.interactor.GetPopularMovies
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import timber.log.Timber

class PopularMoviesViewModel: ViewModel() {

    var isloading: MutableLiveData<Boolean> = MutableLiveData(true)

    var currentPage: MutableLiveData<Int?> = MutableLiveData(0);

    var clickedMovieId: MutableLiveData<Int> = MutableLiveData();

    var popularMovies: MutableLiveData<List<MovieData>> = MutableLiveData()

    var hasError: MutableLiveData<Boolean> = MutableLiveData(false)

    var errorMsg: MutableLiveData<String> = MutableLiveData("")


    var job: Job? = null;

    private var getPopularMovies: GetPopularMovies = GetPopularMovies(MovieRepoImpl(NetworkDao()))

    fun getPopularMoviesAtPage(page:Int){
        hasError.postValue(false)
        currentPage.postValue(page)
        isloading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            var l = getPopularMovies.run(page)
            withContext(Dispatchers.Main){
                isloading.postValue(false)
                l.fold({ handleProblem(it) },
                    { data -> popularMovies.postValue(data) })
            } }    }

    fun nextPage(){
        val i = currentPage.value
        val y = i?.plus(1)
        if (y != null) {
            getPopularMoviesAtPage(y)
        }
    }
    fun prevPage(){
        val i = currentPage.value
        val y = i?.minus(1)
        if (y != null && y > 0) {
            getPopularMoviesAtPage(y)
        }
    }
    fun selectMovie(pos: Int) {
        val m = popularMovies.value?.get(pos)
        if (m != null){
            clickedMovieId.postValue(m.id)
        }
    }

    private fun handleProblem(p: Problem){
        hasError.postValue(true)
        if (p is Problem.NetworkProblem){
            errorMsg.postValue(p.message)
        } else{
            errorMsg.postValue("An error has occurred")
        }

    }
}