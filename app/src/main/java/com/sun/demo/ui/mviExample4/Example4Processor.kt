package com.sun.demo.ui.mviExample4

import android.util.Log
import com.sun.demo.base.MviBaseActionProcessor
import com.sun.demo.data.RepoRepository
import com.sun.demo.ui.mviExample4.Example4Contract.Example4Action
import com.sun.demo.ui.mviExample4.Example4Contract.Example4Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers

class Example4Processor(private val repository: RepoRepository) :
    MviBaseActionProcessor<Example4Action, Example4Result>() {

    private val loadDataActionProcessor =
        ObservableTransformer<Example4Action.LoadDataAction, Example4Result> { action ->
            action.flatMap {
                repository.getMovies()
                    .map(Example4Result::SuccessResult)
                    .cast(Example4Result::class.java)
                    .onErrorReturn {
                        Log.i(this.javaClass.simpleName, it.toString())
                        Example4Result.ErrorResult(it)
                    }
                    .subscribeOn(Schedulers.io())
                    .startWith(Example4Result.LoadingResult)
            }
        }

    private val getLastStateActionProcessor =
        ObservableTransformer<Example4Action.GetLastStateAction, Example4Result> { action ->
            action.map { Example4Result.GetLastResult }
        }

    override val processAction: ObservableTransformer<Example4Action, Example4Result> =
        ObservableTransformer<Example4Action, Example4Result> { action ->
            action.publish { actionSource ->
                Observable.merge(
                    actionSource.ofType(Example4Action.LoadDataAction::class.java)
                        .compose(loadDataActionProcessor),
                    actionSource.ofType(Example4Action.GetLastStateAction::class.java)
                        .compose(getLastStateActionProcessor)
                )
            }
        }
}
