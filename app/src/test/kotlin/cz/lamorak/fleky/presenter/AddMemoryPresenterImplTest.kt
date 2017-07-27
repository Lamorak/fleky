package cz.lamorak.fleky.presenter

import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.model.MemoryDescription
import cz.lamorak.fleky.service.AddMemoryService
import cz.lamorak.fleky.util.assertConnected
import cz.lamorak.fleky.view.AddMemoryViewIntent
import cz.lamorak.fleky.view.AddMemoryViewIntent.*
import cz.lamorak.fleky.view.AddMemoryViewState
import cz.lamorak.fleky.view.AddMemoryViewState.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by ondrej on 26.7.2017.
 */
@RunWith(MockitoJUnitRunner::class)
class AddMemoryPresenterImplTest {

    @Mock
    lateinit var service: AddMemoryService
    lateinit var viewIntents: PublishRelay<AddMemoryViewIntent>
    lateinit var presenter: AddMemoryPresenter
    lateinit var observer: TestObserver<AddMemoryViewState>

    @Before
    fun setUp() {
        viewIntents = PublishRelay.create()
        presenter = AddMemoryPresenterImpl(service)
        observer = TestObserver()
    }

    @Test
    fun testCapture() {
        bindPresenter()
        viewIntents.accept(CreateMemory(null))

        observer.assertConnected()
        observer.assertValue(Capture())
    }

    @Test
    fun testAddDescription() {
        val imageBytes = ByteArray(12)

        bindPresenter()
        viewIntents.accept(CreateMemory(imageBytes))

        observer.assertConnected()
        observer.assertValue(AddDesciption(imageBytes))
    }

    @Test
    fun testSaveMemory() {
        `when`(service.uploadMemory(any(), any())).thenReturn(Observable.empty())
        `when`(service.saveMemory(any(), any())).thenReturn(Completable.complete())

        val imageBytes = ByteArray(12)
        val description = MemoryDescription("", "", 0L)

        bindPresenter()
        viewIntents.accept(SaveMemory(imageBytes, description))

        observer.assertConnected()
        observer.assertValues(Uploading(100), Finished())
    }

    fun bindPresenter() {
        viewIntents.subscribe(presenter.intent())
        presenter.viewState()
                .subscribe(observer)
    }
}