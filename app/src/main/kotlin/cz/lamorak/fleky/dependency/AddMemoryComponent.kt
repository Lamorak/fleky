package cz.lamorak.fleky.dependency

import cz.lamorak.fleky.AddMemoryActivity
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by ondrej on 26.7.2017.
 */
@Singleton
@Component(modules = arrayOf(AddMemoryModule::class, FirebaseModule::class))
interface AddMemoryComponent : AndroidInjector<AddMemoryActivity>
