package cz.lamorak.fleky.dependency

import cz.lamorak.fleky.service.MemoriesService
import cz.lamorak.fleky.service.MockMemoriesService
import cz.lamorak.fleky.presenter.MemoriesPresenter
import cz.lamorak.fleky.presenter.MemoriesPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by ondrej on 15.6.2017.
 */
@Module
class MainModule {

    @Provides
    fun providePresenter(service: MemoriesService) : MemoriesPresenter {
        return MemoriesPresenterImpl(service)
    }

    @Provides
    fun provideService() : MemoriesService {
        return MockMemoriesService()
    }
}