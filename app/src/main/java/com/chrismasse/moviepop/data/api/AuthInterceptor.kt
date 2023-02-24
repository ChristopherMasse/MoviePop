package com.chrismasse.moviepop.data.api

import com.chrismasse.moviepop.ApiKeys
import okhttp3.Interceptor
import okhttp3.Response

/**
    Interceptor to add authorization to API request.
    Does not appear to work with append_to requests
 */
class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val o = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer "+ ApiKeys.MOVIEDB_TOKEN)
            .build()
        return chain.proceed(o)
    }
}