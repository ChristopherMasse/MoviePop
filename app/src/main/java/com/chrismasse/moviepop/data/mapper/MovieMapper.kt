package com.chrismasse.moviepop.data.mapper

import com.chrismasse.moviepop.data.json.ImageJson
import com.chrismasse.moviepop.data.json.MovieData
import com.chrismasse.moviepop.domain.entities.Actor
import com.chrismasse.moviepop.domain.entities.Image
import com.chrismasse.moviepop.domain.entities.Movie
import kotlin.math.roundToInt

class MovieMapper:Mapper<Movie, MovieData> {
    override fun fromModel(m: MovieData): Movie {

        //Round to two decimal places
        val ratingRounded = (m.voteAverage * 10.0).roundToInt()/10.0

        //Use the first genre listed
        var genre = ""
        if (!m.genres.isEmpty()){
            genre = m.genres[0].name
        }

        val logos: ArrayList<Image> = ArrayList()
        val logoData = m.images.logos

        var i = 0
        while (i < logoData.size){
            logos.add(imageJsonToImage(logoData[i]))
            i++
        }

        val backdropData = m.images.backdrops
        val backdrops: ArrayList<Image> = ArrayList()
        var j = 0
        while (j < backdropData.size){
            backdrops.add(imageJsonToImage(backdropData[i]))
            j++
        }

        val posterData = m.images.posters
        val posters: ArrayList<Image> = ArrayList()
        var k = 0
        while (k < posterData.size){
            posters.add(imageJsonToImage(posterData[i]))
            k++
        }



        var actors: ArrayList<Actor> = ArrayList()

        var z = 0;
        val creditsData = m.credits.cast
        while (z < creditsData.size){
            val c = creditsData[i]
            if (c.knownForDept == "Acting"){
                actors.add(Actor(c.name, c.characterName))
            }
            z++
        }

        val t = Movie(
            m.id,
            m.title,
            m.tagline,
            m.overview,
            ratingRounded,
            genre,
            m.releaseDate,
            m.posterPath,
            m.backdropPath,
            logos,
            backdrops,
            posters,
            actors
        )

        return t
    }

    override fun fromModel(models: ArrayList<MovieData>): ArrayList<Movie> {
        var i = 0
        var movies: ArrayList<Movie> = ArrayList()
        while (i < models.size){
            var m:MovieData = models[i]
            movies.add(fromModel(m))
            i++
        }
        return movies
    }

    private fun imageJsonToImage(w:ImageJson):Image{
        return Image(w.filePath, w.aspectRatio, w.height, w.width)
    }
}