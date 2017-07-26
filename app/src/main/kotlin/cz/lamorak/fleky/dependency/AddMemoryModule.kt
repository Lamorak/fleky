package cz.lamorak.fleky.dependency

import cz.lamorak.fleky.presenter.AddMemoryPresenter
import cz.lamorak.fleky.presenter.AddMemoryPresenterImpl
import cz.lamorak.fleky.service.AddMemoryService
import cz.lamorak.fleky.service.MockAddMemoryService
import dagger.Module
import dagger.Provides

/**
 * Created by ondrej on 26.7.2017.
 */
@Module
class AddMemoryModule {

    @Provides
    fun providePresenter(service: AddMemoryService): AddMemoryPresenter {
        return AddMemoryPresenterImpl(service)
    }

    @Provides
    fun provideService(): AddMemoryService {
        return MockAddMemoryService()
    }
}