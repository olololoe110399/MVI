package com.sun.demo.di

import com.sun.demo.ui.mviExample3.Example3ViewModel
import com.sun.demo.ui.mviExample4.Example4ViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * You can create your ViewModel with scope, however it is not required because
 * 1 ViewModel can be used by several LifeCycleOwners.
 */
val viewModelModule = module {
    viewModel { Example3ViewModel(repository = get()) }
    viewModel { Example4ViewModel(repository = get()) }
}
