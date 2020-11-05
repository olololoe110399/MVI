package com.sun.demo.base

import io.reactivex.Observable

interface MviView<I : MviIntent, S : MviViewState> {
    fun intents(): Observable<I>
    fun render(state: S)
}
