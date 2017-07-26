package cz.lamorak.fleky.presenter

import cz.lamorak.fleky.view.AddMemoryViewIntent
import cz.lamorak.fleky.view.AddMemoryViewState
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * Created by ondrej on 26.7.2017.
 */
interface AddMemoryPresenter {

    fun intent(): Consumer<AddMemoryViewIntent>
    fun viewState(): Observable<AddMemoryViewState>
}