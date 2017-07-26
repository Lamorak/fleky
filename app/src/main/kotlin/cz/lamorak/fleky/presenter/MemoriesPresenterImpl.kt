package cz.lamorak.fleky.presenter

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.service.MemoriesService
import cz.lamorak.fleky.util.withLog
import cz.lamorak.fleky.view.MemoriesViewIntent
import cz.lamorak.fleky.view.MemoriesViewState
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class MemoriesPresenterImpl @Inject constructor(private val service: MemoriesService) : MemoriesPresenter {

    private val viewIntentRelay = PublishRelay.create<MemoriesViewIntent>()
    private val viewStateRelay = BehaviorRelay.create<MemoriesViewState>()

    init {
        viewIntentRelay.withLog()
                .concatMap {
                    when (it) {
                        is MemoriesViewIntent.Load -> service.savedMemories()
                                .doOnSubscribe { viewStateRelay.accept(MemoriesViewState.Loading()) }
                                .map { MemoriesViewState.Content(it) as MemoriesViewState }
                                .onErrorReturn { MemoriesViewState.Error(it) }
                    }
                }
                .subscribe(viewStateRelay)
    }

    override fun intent(): Consumer<MemoriesViewIntent> {
        return viewIntentRelay
    }

    override fun viewState(): Observable<MemoriesViewState> {
        return viewStateRelay.withLog()
    }
}