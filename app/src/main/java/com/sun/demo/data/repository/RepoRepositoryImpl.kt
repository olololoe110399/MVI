package com.sun.demo.data.repository

import com.sun.demo.data.RepoRepository
import com.sun.demo.data.repository.source.RepoDataSource
import com.sun.demo.data.model.Movie
import io.reactivex.Observable


class RepoRepositoryImpl(
    private val remote: RepoDataSource.Remote
) : RepoRepository {

    override fun getMovies() = remote.getMovies()
}
