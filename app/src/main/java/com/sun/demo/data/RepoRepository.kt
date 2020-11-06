package com.sun.demo.data

import com.sun.demo.data.model.Movie
import io.reactivex.Observable

interface RepoRepository {

    //Remote
    fun getMovies(): Observable<List<Movie>>
}
