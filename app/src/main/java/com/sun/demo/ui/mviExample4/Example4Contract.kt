package com.sun.demo.ui.mviExample4

import com.sun.demo.base.MviAction
import com.sun.demo.base.MviIntent
import com.sun.demo.base.MviResult
import com.sun.demo.base.MviViewState
import com.sun.demo.data.model.Movie

interface Example4Contract {

    sealed class Example4Intent : MviIntent {
        object LoadDataIntent : Example4Intent()
        object GetLastState : Example4Intent()
    }

    data class Example4ViewState(
        val placeholderState: PlaceholderState = PlaceholderState.Idle,
        val movies: List<Movie> = emptyList()
    ) : MviViewState

    sealed class PlaceholderState {
        object Idle : PlaceholderState()
        object Loading : PlaceholderState()
        data class Error(val error: Throwable) : PlaceholderState()
    }

    sealed class Example4Action : MviAction {
        object LoadDataAction : Example4Action()
        object GetLastStateAction : Example4Action()
    }

    sealed class Example4Result : MviResult {
        object LoadingResult : Example4Result()
        data class ErrorResult(val error: Throwable) : Example4Result()
        data class SuccessResult(val movies: List<Movie>) : Example4Result()
        object GetLastResult : Example4Result()
    }
}
