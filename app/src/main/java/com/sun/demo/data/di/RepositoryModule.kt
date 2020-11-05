package com.sun.demo.data.di

import com.sun.demo.data.RepoRepository
import com.sun.demo.data.repository.RepoRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<RepoRepository> {
        RepoRepositoryImpl(
            remote = get()
        )
    }
}
