package com.sun.demo.ui.mviExample3

import android.util.Log
import com.sun.demo.data.RepoRepository
import com.sun.demo.ui.mviExample3.Example3Contract.Example3Action
import com.sun.demo.ui.mviExample3.Example3Contract.Example3Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers

class Example3Processor(private val repository: RepoRepository) {

    private val loadDataActionProcessor =
        ObservableTransformer<Example3Action.LoadDataAction, Example3Result> { action ->
            action.flatMap {
                repository.getMovies()
                    .map(Example3Result::SuccessResult)
                    .cast(Example3Result::class.java)
                    .onErrorReturn {
                        Log.i(this.javaClass.simpleName, it.toString())
                        Example3Result.ErrorResult(it)
                    }
                    .subscribeOn(Schedulers.io())
                    .startWith(Example3Result.LoadingResult)
            }
        }

    private val getLastStateActionProcessor =
        ObservableTransformer<Example3Action.GetLastStateAction, Example3Result> { action ->
            action.map { Example3Result.GetLastResult }
        }

    val actionProcessor =
        ObservableTransformer<Example3Action, Example3Result> { action ->
            action.publish { actionSource ->
                Observable.merge(
                    actionSource.ofType(Example3Action.LoadDataAction::class.java)
                        .compose(loadDataActionProcessor),
                    actionSource.ofType(Example3Action.GetLastStateAction::class.java)
                        .compose(getLastStateActionProcessor)
                )
            }
        }
}
