package com.sun.demo.ui.mviExample3

import com.sun.demo.data.model.Movie

interface Example3Contract {

    sealed class Example3Intent {
        object LoadDataIntent : Example3Intent()
        object GetLastState : Example3Intent()
    }

    data class Example3ViewState(
        val placeholderState: PlaceholderState = PlaceholderState.Idle,
        val movies: List<Movie> = emptyList()
    )

    sealed class PlaceholderState {
        object Idle : PlaceholderState()
        object Loading : PlaceholderState()
        data class Error(val error: Throwable) : PlaceholderState()
    }

    sealed class Example3Action {
        object LoadDataAction : Example3Action()
        object GetLastStateAction : Example3Action()
    }

    sealed class Example3Result {
        object LoadingResult : Example3Result()
        data class ErrorResult(val error: Throwable) : Example3Result()
        data class SuccessResult(val movies: List<Movie>) : Example3Result()
        object GetLastResult : Example3Result()
    }
}
