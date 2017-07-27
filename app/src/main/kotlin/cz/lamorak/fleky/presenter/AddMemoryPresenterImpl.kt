package cz.lamorak.fleky.presenter

import com.google.firebase.storage.UploadTask
import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.model.Memory
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
                        is SaveMemory -> uploadMemory(it.imageBytes!!, it.memory!!)
                    }
                }
                .subscribe(viewStateRelay)
    }

    private fun createMemory(imageBytes: ByteArray?): Observable<AddMemoryViewState> {
        val viewState = if (imageBytes == null) Capture() else AddDesciption(imageBytes)
        return Observable.just(viewState)
    }

    private fun uploadMemory(imageBytes: ByteArray, memory: MemoryDescription): Observable<AddMemoryViewState> {
        val filename = "${memory.timestamp}"
        return service.uploadMemory(filename, imageBytes)
                .concatMap {
                    when {
                        it.task.isComplete -> saveMemory(filename, createMemory(memory, it))
                        else -> {
                            Observable.just(Uploading() as AddMemoryViewState)
                        }
                    }
                }
    }

    private fun saveMemory(filename: String, memory: Memory): Observable<AddMemoryViewState> {
        return service.saveMemory(filename, memory)
                .andThen(Observable.just(Finished() as AddMemoryViewState))
    }

    private fun createMemory(description: MemoryDescription, taskSnapshot: UploadTask.TaskSnapshot): Memory {
        return Memory(
                id = description.timestamp.toString(),
                title = description.title,
                content = description.comment,
                photo = taskSnapshot.downloadUrl.toString(),
                timestamp = description.timestamp
        )
    }

    override fun intent(): Consumer<AddMemoryViewIntent> {
        return viewIntentRelay
    }

    override fun viewState(): Observable<AddMemoryViewState> {
        return viewStateRelay.withLog()
    }
}