package cz.lamorak.fleky.presenter

import cz.lamorak.fleky.util.Monitor
import cz.lamorak.fleky.view.MemoriesViewIntent
import cz.lamorak.fleky.view.MemoriesViewState
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * Created by ondrej on 25.7.2017.
 */
interface MemoriesPresenter {

    fun intent() : Consumer<MemoriesViewIntent>
    fun viewState() : Observable<MemoriesViewState>
}