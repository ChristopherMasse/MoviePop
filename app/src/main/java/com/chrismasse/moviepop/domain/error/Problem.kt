package com.chrismasse.moviepop.domain.error

interface Problem {
    data class NetworkProblem(val message: String): Problem
}