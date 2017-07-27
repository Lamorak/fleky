package cz.lamorak.fleky.presenter

import com.jakewharton.rxrelay2.PublishRelay
import cz.lamorak.fleky.model.Memory
import cz.lamorak.fleky.service.MemoriesService
import cz.lamorak.fleky.util.assertConnected
import cz.lamorak.fleky.view.DetailViewIntent
import cz.lamorak.fleky.view.DetailViewState
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by ondrej on 27.7.2017.
 */
@RunWith(MockitoJUnitRunner::class)
class DetailPresenterImplTest {

    @Mock
    lateinit var service: MemoriesService
    lateinit var viewIntents: PublishRelay<DetailViewIntent>
    lateinit var presenter: DetailPresenter
    lateinit var observer: TestObserver<DetailViewState>

    @Before
    fun setUp() {
        viewIntents = PublishRelay.create()
        presenter = DetailPresenterImpl(service)
        observer = TestObserver()
    }

    @Test
    fun testLoadingContent() {
        val id = "id"
        Mockito.`when`(service.memory(any())).thenReturn(Observable.just(Memory(id)))

        bindPresenter()
        viewIntents.accept(DetailViewIntent.Load(id))

        observer.assertConnected()
        observer.assertValues(DetailViewState.Loading(), DetailViewState.Content(Memory(id)))
    }

    @Test
    fun testLoadingError() {
        val error = Throwable("error")
        Mockito.`when`(service.memory(any())).thenReturn(Observable.error(error))

        bindPresenter()
        viewIntents.accept(DetailViewIntent.Load("id"))

        observer.assertConnected()
        observer.assertValues(DetailViewState.Loading(), DetailViewState.Error(error))
    }

    fun bindPresenter() {
        viewIntents.subscribe(presenter.intent())
        presenter.viewState()
                .subscribe(observer)
    }
}