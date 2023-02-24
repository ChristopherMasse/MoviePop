package com.chrismasse.moviepop.presentation.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chrismasse.moviepop.data.dao.NetworkDao
import com.chrismasse.moviepop.data.json.CreditJson
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.data.repo.MovieRepoImpl
import com.chrismasse.moviepop.domain.error.Problem
import com.chrismasse.moviepop.domain.interactor.GetMovieDetails
import kotlinx.coroutines.*
import timber.log.Timber

class MovieDetailViewModel: ViewModel() {

    var movieData: MutableLiveData<MovieData> = MutableLiveData()

    var castList: MutableLiveData<List<CreditJson>> = MutableLiveData()

    var job: Job? = null;

    var hasError: MutableLiveData<Boolean> = MutableLiveData(false)

    var errorMsg: MutableLiveData<String> = MutableLiveData("")

    private var getMovieDetails: GetMovieDetails = GetMovieDetails(MovieRepoImpl(NetworkDao()))

    fun getMovieWithId(id:Int){
        hasError.postValue(false)
        Timber.d("Getting movie with  id of $id")
        job = CoroutineScope(Dispatchers.IO).launch {
            var d =getMovieDetails.run(id)
            withContext(Dispatchers.Main){
                d.fold({ handleProblem(it)},
                    { m -> movieData.postValue(m)

                        //Get all the actors
                        var cast:ArrayList<CreditJson> = ArrayList()
                        var credits = m.credits.cast
                        var i = 0;
                        while (i < credits.size){
                            val c = credits[i]
                            if (c.knownForDept == "Acting"){
                                cast.add(c)
                            }
                            i++
                        }
                        castList.postValue(cast)
                    })

            } }
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