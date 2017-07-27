package cz.lamorak.fleky.presenter

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.service.MemoriesService
import cz.lamorak.fleky.util.withLog
import cz.lamorak.fleky.view.DetailViewIntent
import cz.lamorak.fleky.view.DetailViewState
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class DetailPresenterImpl @Inject constructor(private val service: MemoriesService) : DetailPresenter {

    private val viewIntentRelay = PublishRelay.create<DetailViewIntent>()
    private val viewStateRelay = BehaviorRelay.create<DetailViewState>()

    init {
        viewIntentRelay.withLog()
                .concatMap {
                    when (it) {
                        is DetailViewIntent.Load -> service.memory(it.id)
                                .doOnSubscribe { viewStateRelay.accept(DetailViewState.Loading()) }
                                .map { DetailViewState.Content(it) as DetailViewState }
                                .onErrorReturn { DetailViewState.Error(it) }
                    }
                }
                .subscribe(viewStateRelay)
    }

    override fun intent(): Consumer<DetailViewIntent> {
        return viewIntentRelay
    }

    override fun viewState(): Observable<DetailViewState> {
        return viewStateRelay.withLog()
    }
}