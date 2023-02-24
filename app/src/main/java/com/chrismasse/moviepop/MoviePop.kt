package com.chrismasse.moviepop

import android.app.Application
import timber.log.Timber

class MoviePop: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}