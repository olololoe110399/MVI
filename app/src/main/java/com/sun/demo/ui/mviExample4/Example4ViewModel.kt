package com.sun.demo.ui.mviExample4

import android.util.Log
import com.sun.demo.base.MviBaseViewModel
import com.sun.demo.data.RepoRepository
import com.sun.demo.ui.mviExample4.Example4Contract.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class Example4ViewModel(repository: RepoRepository) :
    MviBaseViewModel<Example4Intent, Example4Action, Example4ViewState, Example4Result>(
        Example4Processor(repository),
        Example4ViewState(placeholderState = PlaceholderState.Idle)
    ) {
    private val intentsSubject = PublishSubject.create<Example4Intent>()
    private val states: PublishSubject<Example4ViewState> = PublishSubject.create()
    private val example4Processor = Example4Processor(repository)

    init {
        intentsSubject
            .scan(::intentsFilter)
            .map(::mapToActions)
            .compose(example4Processor.processAction)
            .scan(Example4ViewState(placeholderState = PlaceholderState.Idle), ::reduce)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(states)
    }

    override fun processIntents(intents: Observable<Example4Intent>): Disposable =
        intents.subscribe(intentsSubject::onNext) {
            Log.i(this.javaClass.simpleName, it.message.toString())
        }

    override fun state(): Observable<Example4ViewState> = states.hide()

    override fun mapToActions(intent: Example4Intent): Example4Action = when (intent) {
        Example4Intent.LoadDataIntent -> Example4Action.LoadDataAction
        Example4Intent.GetLastState -> Example4Action.GetLastStateAction
    }

    override fun intentsFilter(initialIntent: Example4Intent, newIntent: Example4Intent) =
        if (newIntent is Example4Intent.LoadDataIntent)
            Example4Intent.GetLastState
        else newIntent

    override fun reduce(previousState: Example4ViewState, result: Example4Result) =
        when (result) {
            is Example4Result.ErrorResult -> previousState.copy(
                placeholderState = PlaceholderState.Error(result.error)
            )
            is Example4Result.SuccessResult -> previousState.copy(
                placeholderState = PlaceholderState.Idle,
                movies = result.movies
            )
            Example4Result.LoadingResult -> previousState.copy(
                placeholderState = PlaceholderState.Loading
            )
            Example4Result.GetLastResult -> previousState
        }
}
