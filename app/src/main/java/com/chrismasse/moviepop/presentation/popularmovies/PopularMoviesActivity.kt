package com.chrismasse.moviepop.presentation.popularmovies

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.chrismasse.moviepop.databinding.ActivityPopularBinding
import com.chrismasse.moviepop.presentation.moviedetail.MovieDetailActivity
import java.util.*

class PopularMoviesActivity: AppCompatActivity(

), PopMovieAdapter.MovieClickListener {

    private val vm: PopularMoviesViewModel by viewModels();
    private lateinit var binding: ActivityPopularBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Setup the view
        binding = ActivityPopularBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        //Observe loading status changes
        vm.isloading.observe(this) {
            showLoading(it)
        }
        //Set Recycler, grid layout
        binding.recycler.layoutManager = GridLayoutManager(this, 2)
        val adapter = PopMovieAdapter(Collections.emptyList(),this)
        binding.recycler.adapter = adapter

        //Observe list changes
        vm.popularMovies.observe(
            this
        ) {
            adapter.setList(it)
        }

        //Observe clicks on the list
        vm.clickedMovieId.observe(
            this
        ) {
            var intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, it)
            startActivity(intent)
        }

        vm.currentPage.observe(this){
            binding.prev.isEnabled = it != 1
            binding.tvPage.text = it.toString()
        }

        binding.next.setOnClickListener {
            vm.nextPage()
        }

        binding.prev.setOnClickListener{
            vm.prevPage()
        }

        vm.hasError.observe(this){
            showError(it)
        }
        vm.errorMsg.observe(this){
            updateErrorMessage(it)
        }

        //Initiate initial page load
        vm.getPopularMoviesAtPage(1)

    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.recycler.visibility = View.INVISIBLE
            binding.loadingView.visibility = View.VISIBLE
        } else{
            binding.recycler.visibility = View.VISIBLE
            binding.loadingView.visibility = View.INVISIBLE
        }
    }

    override fun onMovieClick(pos: Int) {
        vm.selectMovie(pos)
    }

    private fun showError(showMsg: Boolean){
        if (showMsg){
            binding.recycler.visibility = View.GONE
            binding.prev.visibility = View.GONE
            binding.tvPage.visibility = View.GONE
            binding.next.visibility = View.GONE
            binding.errorView.visibility = View.VISIBLE
        } else{
            binding.recycler.visibility = View.VISIBLE
            binding.prev.visibility = View.VISIBLE
            binding.tvPage.visibility = View.VISIBLE
            binding.next.visibility = View.VISIBLE
            binding.errorView.visibility = View.GONE
        }
    }

    private fun updateErrorMessage(msg: String){
        binding.errorMsg.text = msg
    }

}