package cz.lamorak.fleky.presenter

import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.model.MemoryDescription
import cz.lamorak.fleky.service.AddMemoryService
import cz.lamorak.fleky.util.withLog
import cz.lamorak.fleky.view.AddMemoryViewIntent
import cz.lamorak.fleky.view.AddMemoryViewIntent.CreateMemory
import cz.lamorak.fleky.view.AddMemoryViewIntent.SaveMemory
import cz.lamorak.fleky.view.AddMemoryViewState
import cz.lamorak.fleky.view.AddMemoryViewState.*
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class AddMemoryPresenterImpl @Inject constructor(private val service: AddMemoryService) : AddMemoryPresenter {

    private val viewIntentRelay = PublishRelay.create<AddMemoryViewIntent>()
    private val viewStateRelay = PublishRelay.create<AddMemoryViewState>()

    init {
        viewIntentRelay.withLog()
                .concatMap {
                    when (it) {
                        is CreateMemory -> createMemory(it.imageBytes)
                        is SaveMemory -> saveMemory(it.imageBytes!!, it.memory!!)
                    }
                }
                .subscribe(viewStateRelay)
    }

    private fun createMemory(imageBytes: ByteArray?): Observable<AddMemoryViewState> {
        val viewState = if (imageBytes == null) Capture() else AddDesciption(imageBytes)
        return Observable.just(viewState)
    }

    private fun saveMemory(imageBytes: ByteArray, memory: MemoryDescription): Observable<AddMemoryViewState> {
        return service.upload(imageBytes)
                .concatWith(service.saveMemory(memory))
                .andThen(Observable.just(Finished() as AddMemoryViewState))
                .startWith(Uploading())
    }

    override fun intent(): Consumer<AddMemoryViewIntent> {
        return viewIntentRelay
    }

    override fun viewState(): Observable<AddMemoryViewState> {
        return viewStateRelay.withLog()
    }
}