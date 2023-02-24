package com.chrismasse.moviepop.presentation.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chrismasse.moviepop.R
import com.chrismasse.moviepop.data.json.CreditJson
import com.chrismasse.moviepop.databinding.VhCastBinding

class CastAdapter(private var itemSet: List<CreditJson>):
    RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    inner class CastViewHolder(val binding: VhCastBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = VhCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemSet.size
    }

    override fun onBindViewHolder(holder: CastViewHolder, pos: Int) {
        val cast: CreditJson = itemSet[pos]
        holder.binding.tvActorName.text = cast.name
        holder.binding.tvCharacterName.text = cast.characterName
        var path = "https://image.tmdb.org/t/p/original" + cast.profilePath
        Glide.with(holder.itemView.context)
            .load(path)
            .placeholder(R.drawable.cast_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(holder.binding.ivCastProfile)
    }

    fun setList(list: List<CreditJson>){
        itemSet = list
        notifyItemRangeChanged(0, list.size)
    }
}