package com.sun.demo.ui.mviExample1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.view.clicks
import com.sun.demo.R
import io.reactivex.Observable
import com.sun.demo.ui.mviExample1.Example1Contract.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_example_1.*

class Example1Fragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_example_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getIntents()
            .scan(CountViewState(0), ::reduce)
            .subscribe(::render)
            .let(compositeDisposable::add)
    }

    private fun getIntents() =
        Observable.merge(
            listOf(
                incrementButton.clicks().map { CountIntent.Increment },
                decrementButton.clicks().map { CountIntent.Decrement }
            )
        )

    private fun reduce(previousState: CountViewState, intent: CountIntent) =
        when (intent) {
            CountIntent.Increment -> previousState.copy(counter = previousState.counter + 1)
            CountIntent.Decrement -> previousState.copy(counter = previousState.counter - 1)
        }

    private fun render(state: CountViewState) {
        state.counter.toString()
            .let(counterText::setText)
    }

    companion object {
        fun newInstance() = Example1Fragment()
    }
}
