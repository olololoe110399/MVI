package com.sun.demo.data.di

import com.squareup.moshi.Moshi
import com.sun.demo.data.repository.source.remote.api.ApiService
import com.sun.demo.data.repository.source.remote.api.ServiceGenerator
import org.koin.dsl.module

val networkModule = module {

    single<Moshi> {
        Moshi
            .Builder()
            .build()
    }

    single {
        ServiceGenerator.generate(
            serviceClass = ApiService::class.java,
            moshi = get()
        )
    }
}
