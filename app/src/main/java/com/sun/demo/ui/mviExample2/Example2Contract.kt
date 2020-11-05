package com.sun.demo.ui.mviExample2

import com.sun.demo.data.repository.source.remote.model.Movie

interface Example2Contract {

    sealed class Example2Intent {
        object LoadDataIntent : Example2Intent()
    }

    sealed class Example2ViewState {
        object Loading : Example2ViewState()
        data class Error(val e: Throwable) : Example2ViewState()
        data class Success(val movies: List<Movie>) : Example2ViewState()
    }

    sealed class Example2Action {
        object LoadDataAction : Example2Action()
    }

    sealed class Example2Result {
        object LoadingResult : Example2Result()
        data class ErrorResult(val e: Throwable) : Example2Result()
        data class SuccessResult(val movies: List<Movie>) : Example2Result()
    }
}