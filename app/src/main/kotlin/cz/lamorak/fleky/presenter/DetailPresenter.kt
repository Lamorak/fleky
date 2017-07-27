package cz.lamorak.fleky.presenter

import cz.lamorak.fleky.view.DetailViewIntent
import cz.lamorak.fleky.view.DetailViewState
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * Created by ondrej on 27.7.2017.
 */
interface DetailPresenter {

    fun intent() : Consumer<DetailViewIntent>
    fun viewState() : Observable<DetailViewState>
}