package cz.lamorak.fleky.view

import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.model.Memory
import cz.lamorak.fleky.presenter.MemoriesPresenter
import cz.lamorak.fleky.presenter.MemoriesPresenterImpl
import cz.lamorak.fleky.service.MemoriesService
import cz.lamorak.fleky.util.assertConnected
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by ondrej on 25.7.2017.
 */
@RunWith(MockitoJUnitRunner::class)
class MemoriesPresenterImplTest {

    @Mock
    lateinit var service: MemoriesService
    lateinit var viewIntents: PublishRelay<MemoriesViewIntent>
    lateinit var presenter: MemoriesPresenter
    lateinit var observer: TestObserver<MemoriesViewState>

    @Before
    fun setUp() {
        viewIntents = PublishRelay.create()
        presenter = MemoriesPresenterImpl(service)
        observer = TestObserver()
    }

    @Test
    fun testLoadingContent() {
        val testValues = listOf<Memory>()
        `when`(service.savedMemories()).thenReturn(Observable.just(testValues))

        bindPresenter()
        viewIntents.accept(MemoriesViewIntent.Load())

        observer.assertConnected()
        observer.assertValues(MemoriesViewState.Loading(), MemoriesViewState.Content(testValues))
    }

    @Test
    fun testLoadingError() {
        val error = Throwable("error")
        `when`(service.savedMemories()).thenReturn(Observable.error(error))

        bindPresenter()
        viewIntents.accept(MemoriesViewIntent.Load())

        observer.assertConnected()
        observer.assertValues(MemoriesViewState.Loading(), MemoriesViewState.Error(error))
    }

    fun bindPresenter() {
        viewIntents.subscribe(presenter.intent())
        presenter.viewState()
                .subscribe(observer)
    }
}