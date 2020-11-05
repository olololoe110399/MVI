package com.sun.demo.data.di

import com.sun.demo.data.repository.source.RepoDataSource
import com.sun.demo.data.repository.source.remote.RepoRemoteImpl
import org.koin.dsl.module

val dataSourceModule = module {
    /**
     * Local setting module
     */

    /**
     * Data source module
     */

    single<RepoDataSource.Remote> {
        RepoRemoteImpl(
            apiService = get()
        )
    }
}
