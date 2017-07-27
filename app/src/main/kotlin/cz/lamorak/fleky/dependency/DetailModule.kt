package cz.lamorak.fleky.dependency

import com.google.firebase.database.DatabaseReference
import cz.lamorak.fleky.presenter.DetailPresenter
import cz.lamorak.fleky.presenter.DetailPresenterImpl
import cz.lamorak.fleky.service.MemoriesService
import cz.lamorak.fleky.service.MemoriesServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Created by ondrej on 27.7.2017.
 *
 */
@Module
class DetailModule{

    @Provides
    fun providePresenter(service: MemoriesService) : DetailPresenter {
        return DetailPresenterImpl(service)
    }

    @Provides
    fun provideService(databaseReference: DatabaseReference) : MemoriesService {
        return MemoriesServiceImpl(databaseReference)
    }
}