package com.sun.demo.ui.mviExample3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.view.clicks
import com.sun.demo.R
import com.sun.demo.data.model.Movie
import com.sun.demo.ui.mviExample3.Example3Contract.*
import com.sun.demo.util.gone
import com.sun.demo.util.visible
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_example_3.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class Example3Fragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private val viewModel: Example3ViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_example_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state()
            .subscribe(::render) {
                Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
            .let(compositeDisposable::add)
        intents()
            .let(viewModel::processIntents)
            .let(compositeDisposable::add)
    }

    private fun intents(): Observable<Example3Intent> =
        Observable.merge(
            listOf(
                Observable.just(Example3Intent.LoadDataIntent),
                subscribeLoadMoviesButton.clicks()
                    .map { Example3Intent.LoadDataIntent })
        )

    private fun render(state: Example3ViewState) {
        when (state.placeholderState) {
            PlaceholderState.Loading -> renderLoading(true)
            PlaceholderState.Idle -> {
                renderLoading(false)
                if (state.movies.isNotEmpty())
                    renderSuccess(state.movies)
            }
            is PlaceholderState.Error -> {
                renderLoading(false)
                renderError(state.placeholderState.error)
            }
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        if (isLoading) {
            display.gone()
            progressBar.visible()
        } else {
            display.visible()
            progressBar.gone()
        }
    }

    private fun renderSuccess(movies: List<Movie>) {
        display.text = movies.toString()
    }

    private fun renderError(e: Throwable) {
        display.text = e.message.toString()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = Example3Fragment()
    }
}
