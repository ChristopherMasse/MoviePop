package com.chrismasse.moviepop.presentation.moviedetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chrismasse.moviepop.data.json.CreditJson
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.databinding.ActivityDetailBinding
import java.util.*
import kotlin.math.roundToInt

class MovieDetailActivity: AppCompatActivity() {

    companion object {
        val EXTRA_MOVIE_ID: String = "extra_movie_id"
    }

    private val vm: MovieDetailViewModel by viewModels()

    lateinit var castAdapter: CastAdapter

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //Setup the view
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val id= intent.getIntExtra(EXTRA_MOVIE_ID,-1)

        vm.getMovieWithId(id)

        vm.movieData.observe(this) {
            showMovieDetails(it)
        }

        binding.recyclerCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castAdapter = CastAdapter(Collections.emptyList())
        binding.recyclerCast.adapter = castAdapter

        vm.castList.observe(this){
            showCastDetails(it)
        }

        vm.hasError.observe(this){
            showError(it)
        }
        vm.errorMsg.observe(this){
            updateErrorMessage(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true;
    }

    private fun showMovieDetails(m: MovieData){
        //Set the backdrop
        val path = "https://image.tmdb.org/t/p/original" + m.backdropPath
        Glide.with(this)
            .load(path)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imgBackdrop)
        //Set the title
        binding.tvTitle.text = m.title
        //Set the tagline if available
        if (m.tagline.equals("")){
            binding.tvTagline.visibility = View.GONE
        } else{
            binding.tvTagline.text = m.tagline
        }

        //Genre
        binding.tvGenrePrimary.text = m.genres[0].name

        //Rating
        val rating = m.voteAverage
        val rounded = (rating * 10.0).roundToInt()/10.0

        binding.tvRating.text = rounded.toString()
        //Set description
        binding.tvOverview.text = m.overview

        //Release Data
        var releaseStr:String = "Release Date: " + m.releaseDate
        binding.tvReleaseDate.text = releaseStr

        //Set Poster
        val posterPath = "https://image.tmdb.org/t/p/original" + m.posterPath
        Glide.with(this)
            .load(posterPath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imgPoster)

        //Add other photos if possible
        val images = m.images

        // Add a logo, if available
        if (images.logos.isNotEmpty()){
            val path2 = "https://image.tmdb.org/t/p/original" + m.images.logos[0].filePath
            Glide.with(this)
                .load(path2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgSecond)
        }
        // Add another backdrop if available

        if (images.backdrops.size > 1){
            val path3 = "https://image.tmdb.org/t/p/original" + m.images.backdrops[1].filePath
            Glide.with(this)
                .load(path3)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgThird)
        }

        // Add another backdrop if available
        if (images.backdrops.size > 2){
            val path4 = "https://image.tmdb.org/t/p/original" + m.images.backdrops[2].filePath
            Glide.with(this)
                .load(path4)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgFourth)
        }
        // Add another poster if available
        if (images.posters.size > 1){
            val path5 = "https://image.tmdb.org/t/p/original" + m.images.posters[1].filePath
            Glide.with(this)
                .load(path5)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgFifth)
        }


    }

    private fun showCastDetails(cast: List<CreditJson>){
        castAdapter.setList(cast)
    }

    private fun showError(showMsg: Boolean){
        if (showMsg){
            binding.scrollview.visibility = View.GONE
            binding.errorView.visibility = View.VISIBLE
        } else{
            binding.scrollview.visibility = View.VISIBLE
            binding.errorView.visibility = View.GONE
        }
    }

    private fun updateErrorMessage(msg: String){
        binding.errorMsg.text = msg
    }


}