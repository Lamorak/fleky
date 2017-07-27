package cz.lamorak.fleky.dependency

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import cz.lamorak.fleky.presenter.AddMemoryPresenter
import cz.lamorak.fleky.presenter.AddMemoryPresenterImpl
import cz.lamorak.fleky.service.AddMemoryService
import cz.lamorak.fleky.service.AddMemoryServiceImpl
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
    fun provideService(storageReference: StorageReference, databaseReference: DatabaseReference): AddMemoryService {
        return AddMemoryServiceImpl(storageReference, databaseReference)
    }
}