package com.sun.demo.data.repository

import com.sun.demo.data.RepoRepository
import com.sun.demo.data.repository.source.RepoDataSource


class RepoRepositoryImpl(
    private val remote: RepoDataSource.Remote
) : RepoRepository {

    override fun getMovies() = remote.getMovies()
}
