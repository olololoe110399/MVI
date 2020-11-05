package com.sun.demo.ui.mviExample2

import com.sun.demo.data.RepoRepository
import com.sun.demo.ui.mviExample2.Example2Contract.*
import io.reactivex.ObservableTransformer
import org.koin.core.KoinComponent
import org.koin.core.inject

class Example2Processor : KoinComponent {
    private val repoRepository by inject<RepoRepository>()

    private val loadDataActionProcessor =
        ObservableTransformer<Example2Action.LoadDataAction, Example2Result> { action ->
            action.flatMap {
                repoRepository.getMovies()
                    .map(Example2Result::SuccessResult)
                    .cast(Example2Result::class.java)
                    .onErrorReturn { Example2Result.ErrorResult(it) }
                    .startWith(Example2Result.LoadingResult)
            }
        }
    val actionProcessor =
        ObservableTransformer<Example2Action, Example2Result> { action ->
            action.publish { actionSource ->
                actionSource.ofType(Example2Action.LoadDataAction::class.java)
                    .compose(loadDataActionProcessor)
            }
        }
}