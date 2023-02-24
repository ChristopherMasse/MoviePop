package com.chrismasse.moviepop.data.mapper

interface Mapper<T, M> {

    fun fromModel(model:M): T

    fun fromModel(models:ArrayList<M>): ArrayList<T>

//    fun toModel(entity: T): M

//    fun toModel(entities: Collection<T>): Collection<M>
}