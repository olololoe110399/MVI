package com.sun.demo.ui.mviExample1

interface Example1Contract {

    sealed class CountIntent {
        object Increment : CountIntent()
        object Decrement : CountIntent()
    }

    data class CountViewState(val counter: Int)
}