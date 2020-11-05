package com.sun.demo.data.repository.source.remote.api

import com.sun.demo.data.repository.source.remote.model.Movie
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("51f958b5588421b9ae5089b5113b7d77/raw/67f9daa4d1687d497fcef81fae1ce4a4b71b5af6/movies")
    fun getMovies(): Observable<List<Movie>>
}
