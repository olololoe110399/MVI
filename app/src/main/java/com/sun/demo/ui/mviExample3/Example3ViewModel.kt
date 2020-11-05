package com.sun.demo.ui.mviExample3

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sun.demo.data.RepoRepository
import com.sun.demo.ui.mviExample3.Example3Contract.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class Example3ViewModel(repository: RepoRepository) : ViewModel() {
    private val intentsSubject = PublishSubject.create<Example3Intent>()
    private val states: PublishSubject<Example3ViewState> = PublishSubject.create()
    private val example3Processor = Example3Processor(repository)

    init {
        intentsSubject
            .scan(::intentsFilter)
            .map(::mapToActions)
            .compose(example3Processor.actionProcessor)
            .scan(Example3ViewState(placeholderState = PlaceholderState.Idle), ::reduce)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(states)
    }

    fun processIntents(intents: Observable<Example3Intent>): Disposable =
        intents.subscribe(intentsSubject::onNext) {
            Log.i(this.javaClass.simpleName, it.message.toString())
        }

    fun state(): Observable<Example3ViewState> = states.hide()

    fun mapToActions(intent: Example3Intent): Example3Action = when (intent) {
        Example3Intent.LoadDataIntent -> Example3Action.LoadDataAction
        Example3Intent.GetLastState -> Example3Action.GetLastStateAction
    }

    private fun intentsFilter(initialIntent: Example3Intent, newIntent: Example3Intent) =
        if (newIntent is Example3Intent.LoadDataIntent)
            Example3Intent.GetLastState
        else newIntent

    override fun onCleared() {
        intentsSubject.onComplete()
        states.onComplete()
        super.onCleared()
    }

    private fun reduce(previousState: Example3ViewState, result: Example3Result) =
        when (result) {
            is Example3Result.ErrorResult -> previousState.copy(
                placeholderState = PlaceholderState.Error(result.error)
            )
            is Example3Result.SuccessResult -> previousState.copy(
                placeholderState = PlaceholderState.Idle,
                movies = result.movies
            )
            Example3Result.LoadingResult -> previousState.copy(
                placeholderState = PlaceholderState.Loading
            )
            Example3Result.GetLastResult -> previousState
        }
}
