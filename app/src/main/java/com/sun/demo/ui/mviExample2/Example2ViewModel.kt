package com.sun.demo.ui.mviExample2

import com.sun.demo.ui.mviExample2.Example2Contract.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Example2ViewModel {
    private val example2Processor = Example2Processor()

    fun bind(intents: Observable<Example2Intent>): Observable<Example2ViewState> =
        intents.map(::mapToActions).compose(example2Processor.actionProcessor)
            .scan(Example2ViewState.Loading, ::reduce)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun mapToActions(intent: Example2Intent) = when (intent) {
        Example2Intent.LoadDataIntent -> Example2Action.LoadDataAction
    }

    private fun reduce(previousState: Example2ViewState, result: Example2Result) =
        when (result) {
            is Example2Result.ErrorResult -> Example2ViewState.Error(result.error)
            is Example2Result.SuccessResult -> Example2ViewState.Success(result.movies)
            Example2Result.LoadingResult -> Example2ViewState.Loading
        }
}
