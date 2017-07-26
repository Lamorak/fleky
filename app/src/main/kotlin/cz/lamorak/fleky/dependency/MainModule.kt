package cz.lamorak.fleky.dependency

import cz.lamorak.fleky.service.MemoriesService
import cz.lamorak.fleky.service.MockMemoriesService
import dagger.Module
import dagger.Provides

/**
 * Created by ondrej on 15.6.2017.
 */
@Module
class MainModule {

    @Provides
    fun provideService() : MemoriesService {
        return MockMemoriesService()
    }
}