package com.sun.demo.ui.mviExample2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.demo.R
import com.sun.demo.data.model.Movie
import com.sun.demo.ui.mviExample2.Example2Contract.Example2Intent
import com.sun.demo.ui.mviExample2.Example2Contract.Example2ViewState
import com.sun.demo.util.gone
import com.sun.demo.util.visible
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_example_2.*

class Example2Fragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private val viewModel = Example2ViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_example_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLoadMoviesButton.setOnClickListener {
            intents()
                .let(viewModel::bind)
                .subscribe(::render) {
                    Log.i(this.javaClass.simpleName, it.message.toString())
                }
                .let(compositeDisposable::add)
        }
    }

    private fun intents(): Observable<Example2Intent> =
        Observable.merge(
            listOf(Observable.just(Example2Intent.LoadDataIntent))
        )

    private fun render(state: Example2ViewState) = when (state) {
        Example2ViewState.Loading -> renderLoading()
        is Example2ViewState.Success -> renderSuccess(state.movies)
        is Example2ViewState.Error -> renderError(state.error)
    }

    private fun renderLoading() {
        display.gone()
        progressBar.visible()
    }

    private fun renderSuccess(movies: List<Movie>) {
        display.visible()
        progressBar.gone()
        display.text = movies.toString()
    }

    private fun renderError(e: Throwable) {
        display.visible()
        progressBar.gone()
        display.text = e.message.toString()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = Example2Fragment()
    }
}
