package com.sun.demo.base

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class MviBaseViewModel<I : MviIntent, A : MviAction, S : MviViewState, R : MviResult>(
    actionProcessor: MviBaseActionProcessor<A, R>,
    initialState: S
) : ViewModel(), MviViewModel<I, S> {

    private val intentsSubject = PublishSubject.create<I>()
    private val states: PublishSubject<S> = PublishSubject.create()

    init {
        intentsSubject
            .scan(::intentsFilter)
            .map(::mapToActions)
            .compose(actionProcessor.processAction)
            .scan(initialState, ::reduce)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(states)
    }

    override fun processIntents(intents: Observable<I>): Disposable =
        intents.subscribe(intentsSubject::onNext) {
            Log.i(this.javaClass.simpleName, it.message.toString())
        }

    override fun state(): Observable<S> = states.hide()

    override fun onCleared() {
        intentsSubject.onComplete()
        states.onComplete()
        super.onCleared()
    }

    abstract fun intentsFilter(initialIntent: I, newIntent: I): I

    abstract fun mapToActions(intent: I): A

    abstract fun reduce(previousState: S, result: R): S
}
