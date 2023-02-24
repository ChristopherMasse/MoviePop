package com.chrismasse.moviepop.presentation.popularmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.databinding.VhPopMovieBinding
import kotlin.math.roundToInt

class PopMovieAdapter(private var itemSet: List<MovieData>, private val listener: MovieClickListener):
    RecyclerView.Adapter<PopMovieAdapter.PopMovieViewHolder>() {

    inner class PopMovieViewHolder(val binding: VhPopMovieBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                listener.onMovieClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopMovieViewHolder {
        val binding = VhPopMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PopMovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemSet.size
    }

    override fun onBindViewHolder(holder: PopMovieViewHolder, position: Int) {
        val movie = itemSet[position]
        holder.binding.txtTitle.text = movie.title

        val rating = movie.voteAverage
        val rounded = (rating * 10.0).roundToInt()/10.0
        holder.binding.tvRating.text = rounded.toString()



        var path = "https://image.tmdb.org/t/p/original" + movie.posterPath
        Glide.with(holder.itemView.context)
            .load(path)
//            .placeholder(R.drawable.placeholder_pop)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.imgPoster)

    }

    fun setList(list: List<MovieData>){
        itemSet = list
        notifyItemRangeChanged(0, list.size)
    }

    interface MovieClickListener{
        fun onMovieClick(pos: Int)
    }
}