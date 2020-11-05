package com.sun.demo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class MviBaseFragment<I : MviIntent, A : MviAction, S : MviViewState, R : MviResult> :
    MviView<I, S>, Fragment() {

    private val compositeDisposable = CompositeDisposable()
    abstract val viewModel: MviBaseViewModel<I, A, S, R>

    abstract val layout: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout, container, false)

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

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
}
