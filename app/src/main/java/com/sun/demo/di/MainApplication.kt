package com.sun.demo.di

import android.app.Application
import com.sun.demo.data.di.dataSourceModule
import com.sun.demo.data.di.networkModule
import com.sun.demo.data.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val modules = listOf(
            networkModule,
            dataSourceModule,
            repositoryModule,
            viewModelModule
        )
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(modules)
        }
    }
}
