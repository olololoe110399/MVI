package com.sun.demo.data.repository.source.remote

import com.sun.demo.data.repository.source.RepoDataSource
import com.sun.demo.data.repository.source.remote.api.ApiService

class RepoRemoteImpl(private val apiService: ApiService) : RepoDataSource.Remote {

    override fun getMovies() = apiService.getMovies()
}
