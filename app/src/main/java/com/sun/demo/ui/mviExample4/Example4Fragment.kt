package com.sun.demo.ui.mviExample4

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding3.view.clicks
import com.sun.demo.R
import com.sun.demo.base.MviBaseFragment
import com.sun.demo.data.model.Movie
import com.sun.demo.ui.mviExample4.Example4Contract.*
import com.sun.demo.ui.mviExample4.adapter.MovieListAdapter
import com.sun.demo.util.gone
import com.sun.demo.util.visible
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_example_4.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class Example4Fragment :
    MviBaseFragment<Example4Intent, Example4Action, Example4ViewState, Example4Result>() {
    private val movieListAdapter by lazy {
        MovieListAdapter() {
            // handle click item
        }
    }

    override val viewModel: Example4ViewModel by viewModel()

    override val layout = R.layout.fragment_example_4

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
    }

    private fun setUpRecycler() {
        moviesRecyclerView.apply {
            setHasFixedSize(true)
            adapter = movieListAdapter
        }
    }

    override fun intents(): Observable<Example4Intent> =
        Observable.merge(
            listOf(
                Observable.just(Example4Intent.LoadDataIntent),
                loadMovies.clicks()
                    .map { Example4Intent.LoadDataIntent })
        )

    override fun render(state: Example4ViewState) =
        when (state.placeholderState) {
            PlaceholderState.Loading -> renderLoading(true)
            PlaceholderState.Idle -> {
                renderLoading(false)
                renderSuccess(state.movies)
            }
            is PlaceholderState.Error -> {
                renderLoading(false)
                renderError(state.placeholderState.error)
            }
        }

    private fun renderLoading(isLoading: Boolean) {
        if (isLoading) {
            moviesRecyclerView.gone()
            progressBar.visible()
        } else {
            moviesRecyclerView.visible()
            progressBar.gone()
        }
    }

    private fun renderSuccess(movies: List<Movie>) {
        movieListAdapter.submitList(movies)
    }

    private fun renderError(e: Throwable) {
        Toast.makeText(activity, e.message.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = Example4Fragment()
    }
}
