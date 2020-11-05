package com.sun.demo.base

import io.reactivex.ObservableTransformer

abstract class MviBaseActionProcessor<A : MviAction, R : MviResult> {

    abstract val processAction: ObservableTransformer<A, R>
}
